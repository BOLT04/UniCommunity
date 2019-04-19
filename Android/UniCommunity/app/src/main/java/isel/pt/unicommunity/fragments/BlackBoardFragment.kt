package isel.pt.unicommunity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.R
import isel.pt.unicommunity.adapters.BlackBoardAdapter
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.viewmodel.BlackBoardViewModel
import kotlinx.android.synthetic.main.fragment_all_boards.*


class BlackBoardFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_all_boards, container, false)



    override fun onStart() {
        super.onStart()

        val viewModel = (activity as AppCompatActivity).getViewModel("blackBoard"){
            BlackBoardViewModel(/*id!!*/)//TODO double bangs onde e que ha mesmo a verificaçao
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        /*val onBoardClickListener = object : BoardClickListener {
            override fun onClickListener(smallBoardItem: SmallBoardItem?) {

                (activity as MainActivity).navigateTo(BoardMenuFragment())

                Toast.makeText(activity, smallBoardItem?.name ?: "nullsmall board item", Toast.LENGTH_LONG).show()
            }
        }*/
        recyclerView.adapter = BlackBoardAdapter(viewModel)

        viewModel.liveData.observe(
            this,
            Observer { recyclerView.adapter =  BlackBoardAdapter(viewModel)}
        )
    }



}