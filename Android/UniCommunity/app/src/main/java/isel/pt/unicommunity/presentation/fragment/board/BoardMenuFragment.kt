package isel.pt.unicommunity.presentation.fragment.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.presentation.adapter.BoardMenuAdapter
import isel.pt.unicommunity.presentation.adapter.BlackBoardClickListener
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleBlackBoardLink
import isel.pt.unicommunity.model.links.GetSingleBoardLink
import isel.pt.unicommunity.model.links.GetSingleForumLink
import isel.pt.unicommunity.presentation.activity.BackStackManagingActivity
import isel.pt.unicommunity.presentation.adapter.ForumClickListener
import isel.pt.unicommunity.presentation.fragment.modules.blackboard.BlackBoardFragment
import isel.pt.unicommunity.presentation.fragment.modules.forum.ForumFragment
import isel.pt.unicommunity.presentation.viewmodel.BoardViewModel
import kotlinx.android.synthetic.main.fragment_all_boards.*

class BoardMenuFragment(val link: GetSingleBoardLink) : Fragment() {


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

        val viewModel = activity.getViewModel("boardID${link.href}"){
            BoardViewModel(app, link)
        }


        recyclerView.layoutManager = LinearLayoutManager(activity)

        val progress = OptionalProgressBar(activity) {
            viewModel.fetchBoard()
        }


        viewModel.boardLd.observe(
            this,
             ProgressObs(progress) {

                val smallBlackBoards = it._embedded?.smallBlackBoardBoards

                if(smallBlackBoards == null)
                    viewModel.fetchModules(it._links.getMultipleBlackBoardsLink, it._links.getSingleForumLink)
                else
                    viewModel.fillModules( smallBlackBoards, it )

            },
            ProgressObs(progress) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )


        val forumClickListener = object :
            ForumClickListener {
            override fun onClickListener(forumLink: GetSingleForumLink) {
                (activity as BackStackManagingActivity).navigateTo(ForumFragment(forumLink))
            }
        }

        val blackBoardClickListener = object :
            BlackBoardClickListener {
            override fun onClickListener(blackBoardLink: GetSingleBlackBoardLink) {
                (activity as BackStackManagingActivity).
                    navigateTo(BlackBoardFragment(blackBoardLink))
            }
        }

        viewModel.menuLd.observe(
            this,
            ProgressObs(progress) {
                recyclerView.adapter =
                    BoardMenuAdapter(
                        it,
                        blackBoardClickListener,
                        forumClickListener
                    )
            },
            ProgressObs(progress) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )

    }

}