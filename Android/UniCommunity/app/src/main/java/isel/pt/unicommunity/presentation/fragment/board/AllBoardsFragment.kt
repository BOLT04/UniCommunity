package isel.pt.unicommunity.presentation.fragment.board

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.presentation.activity.BackStackManagingActivity

import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp

import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetMultipleBoardsLink
import isel.pt.unicommunity.model.collectionjson.BoardCollection
import isel.pt.unicommunity.presentation.adapter.AllBoardsAdapter
import isel.pt.unicommunity.presentation.adapter.PartialBoardItemClickListener
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

        val progress = ProgressDialog(activity)
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog*/

        progress.show()
        viewModel.getAllBoards{progress.dismiss()}

        recyclerView.layoutManager = LinearLayoutManager(activity)

        val onPartialBoardItemClickListener = object : PartialBoardItemClickListener {
            override fun onClickListener(partialBoardIem: BoardCollection.PartialBoard) {

                (activity as BackStackManagingActivity).navigateTo(BoardMenuFragment(partialBoardIem.self))

                Toast.makeText(activity, partialBoardIem.self.href , Toast.LENGTH_LONG).show()

            }

        }

        viewModel.allBoards.observe(this, Observer {

            recyclerView.adapter =  AllBoardsAdapter(
                viewModel.allBoards.value?.boards ?: mutableListOf(),
                onPartialBoardItemClickListener
            )

        })


    }

}
