package isel.pt.unicommunity.presentation.fragment.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
/*import isel.pt.unicommunity.presentation.adapter.BoardClickListener
import isel.pt.unicommunity.presentation.adapter.old.MyBoardsAdapter*/
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.MyBoardsLink
import isel.pt.unicommunity.presentation.activity.BackStackManagingActivity
import isel.pt.unicommunity.presentation.adapter.GenericMyBoardsAdapter
import isel.pt.unicommunity.presentation.adapter.OnClickListener
import isel.pt.unicommunity.presentation.adapter.PartialBoardView
import isel.pt.unicommunity.presentation.viewmodel.MyBoardsViewModel
import kotlinx.android.synthetic.main.fragment_all_boards.recyclerView

class MyBoardsFragment(private val myBoards: MyBoardsLink) : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_my_boards, container, false)
        }

        override fun onStart() {
            super.onStart()

            val app = (activity as AppCompatActivity).getUniCommunityApp()

            val viewModel = (activity as AppCompatActivity).getViewModel("MyBoards${myBoards.href}"){
                MyBoardsViewModel(myBoards, app)
            }

            recyclerView.layoutManager = LinearLayoutManager(activity)

            viewModel.getMyBoards()

            val onBoardClickListener = object : OnClickListener<PartialBoardView> {
                override fun onClick(value: PartialBoardView) {

                    (activity as BackStackManagingActivity).navigateTo(BoardMenuFragment(value.self))

                    Toast.makeText(activity, value.name, Toast.LENGTH_LONG).show()
                }
            }


            viewModel.myboardsLD.observe(
                this,
                Observer {
                    recyclerView.adapter = GenericMyBoardsAdapter(it, onBoardClickListener)
                },
                Observer { Toast.makeText(activity, it, Toast.LENGTH_LONG).show() }
            )

        }

}
