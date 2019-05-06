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
import isel.pt.unicommunity.BackStackManagingActivity

import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.adapter.BoardClickListener
import isel.pt.unicommunity.presentation.adapter.MyBoardsAdapter
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.refactor_me_dad.small.SmallBoardItem
import isel.pt.unicommunity.presentation.viewmodel.MyBoardsViewModel
import kotlinx.android.synthetic.main.fragment_all_boards.*

class MyBoardsFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_my_boards, container, false)
        }

        override fun onStart() {
            super.onStart()

            val viewModel = (activity as AppCompatActivity).getViewModel("MyBoards"){
                MyBoardsViewModel()
            }

            recyclerView.layoutManager = LinearLayoutManager(activity)
            val onBoardClickListener = object : BoardClickListener {
                override fun onClickListener(smallBoardItem: SmallBoardItem?) {

                    (activity as BackStackManagingActivity).navigateTo(BoardMenuFragment())

                    Toast.makeText(activity, smallBoardItem?.name ?: "nullsmall board item", Toast.LENGTH_LONG).show()
                }
            }
            recyclerView.adapter = MyBoardsAdapter(viewModel, onBoardClickListener)

            viewModel.liveData.observe(
                this,
                Observer<List<SmallBoardItem>> { recyclerView.adapter =  MyBoardsAdapter(viewModel, onBoardClickListener)}
            )
        }

}
