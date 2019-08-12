package isel.pt.unicommunity.presentation.fragment.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.presentation.adapter.BoardMenuAdapter
import isel.pt.unicommunity.presentation.adapter.BlackBoardClickListener
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.webdto.rel_links.GetSingleBlackBoardLink
import isel.pt.unicommunity.model.webdto.rel_links.GetSingleBoardLink
import isel.pt.unicommunity.model.webdto.rel_links.GetSingleForumLink
import isel.pt.unicommunity.model.webdto.rel_links.PartialBoardItem
import isel.pt.unicommunity.presentation.adapter.ForumClickListener
import isel.pt.unicommunity.presentation.fragment.modules.blackboard.BlackBoardFragment
import isel.pt.unicommunity.presentation.fragment.modules.forum.ForumFragment
import isel.pt.unicommunity.presentation.viewmodel.BoardViewModel
import kotlinx.android.synthetic.main.fragment_all_boards.*

class BoardMenuFragment(val partialBoardIem: PartialBoardItem) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_boards, container, false)
    }

    override fun onStart() {
        super.onStart()

        val activity = (activity as AppCompatActivity)

        val app = activity.getUniCommunityApp()

        val viewModel = activity.getViewModel("boardID${partialBoardIem.href}"){
            BoardViewModel(app, GetSingleBoardLink(partialBoardIem.href))
        }


        recyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.fetchBoard()

        viewModel.board.observe(this, Observer { board ->
            val smallBlackBoards = board._embedded?.smallBlackBoardBoards
            if(smallBlackBoards == null){
                viewModel.fetchModules(board._links.getMultipleBlackBoardsLink, board._links.getSingleForumLink)
            }
            else{

                viewModel.menu.value = BoardViewModel.MenuView(

                    smallBlackBoards.map {
                        val name = it.name
                        val link = it._links.self

                        if(name == null || link == null){
                            viewModel.fetchModules(board._links.getMultipleBlackBoardsLink, board._links.getSingleForumLink)
                            return@Observer
                        }

                        BoardViewModel.EvenSmallerBlackBoard(
                            name, link)
                    },
                    board._links.getSingleForumLink
                )
            }
        })


        val forumClickListener = object : ForumClickListener{
            override fun onClickListener(forumLink: GetSingleForumLink) {
                Toast.makeText(activity, forumLink.href , Toast.LENGTH_LONG).show()
                (activity as MainActivity).navigateTo(
                    ForumFragment(
                        forumLink
                    )
                )
            }
        }

        val blackBoardClickListener = object : BlackBoardClickListener{
            override fun onClickListener(blackBoardLink: GetSingleBlackBoardLink) {
                Toast.makeText(activity, blackBoardLink.href , Toast.LENGTH_LONG).show()
                (activity as MainActivity).navigateTo(
                    BlackBoardFragment(
                        blackBoardLink
                    )
                )
            }
        }

        viewModel.menu.observe(this, Observer {
            recyclerView.adapter =  BoardMenuAdapter(
                it,
                blackBoardClickListener,
                forumClickListener
            )
        })

    }

}