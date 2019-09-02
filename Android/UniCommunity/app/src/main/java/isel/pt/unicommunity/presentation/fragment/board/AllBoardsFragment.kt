package isel.pt.unicommunity.presentation.fragment.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.presentation.activity.BackStackManagingActivity

import isel.pt.unicommunity.R
import isel.pt.unicommunity.common.OptionalProgressBar
import isel.pt.unicommunity.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp

import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetMultipleBoardsLink
import isel.pt.unicommunity.model.collectionjson.BoardCollection
import isel.pt.unicommunity.presentation.adapter.GenericAllBoardsAdapter
import isel.pt.unicommunity.presentation.adapter.OnClickListener
import isel.pt.unicommunity.presentation.adapter.PartialBoardView
import isel.pt.unicommunity.presentation.viewmodel.AllBoardsViewModel
import kotlinx.android.synthetic.main.fragment_all_boards.*


class AllBoardsFragment(val link: GetMultipleBoardsLink) : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_boards, container, false)
    }

    override fun onStart() {
        super.onStart()

        val activity = (activity as AppCompatActivity)

        val viewModel = activity.getViewModel("allBoards"){
            AllBoardsViewModel(activity.getUniCommunityApp(), link)
        }


        val progress = OptionalProgressBar(activity) {
            viewModel.getAllBoards()
        }


        recyclerView.layoutManager = LinearLayoutManager(activity)


        val onPartialBoardItemClickListener = object : OnClickListener<PartialBoardView> {
            override fun onClick(value: PartialBoardView) {
                (activity as BackStackManagingActivity).navigateTo(BoardMenuFragment(value.self))

                Toast.makeText(activity, value.self.href , Toast.LENGTH_LONG).show()
            }
        }

        viewModel.allBoardsLd.observe(
            this,
            ProgressObs(progress) {
                recyclerView.adapter =  GenericAllBoardsAdapter(
                    it.boards.map {
                        board ->
                            PartialBoardView(
                                board.self,
                                board.name,
                                board.id,
                                board.description
                            )
                    },
                    onPartialBoardItemClickListener
                )
            },
            ProgressObs(progress) {
                Toast.makeText(activity, it , Toast.LENGTH_LONG).show()
            }
        )




    }

}

