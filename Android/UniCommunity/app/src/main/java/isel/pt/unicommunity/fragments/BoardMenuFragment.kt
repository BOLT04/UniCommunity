package isel.pt.unicommunity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.adapters.BoardMenuAdapter
import isel.pt.unicommunity.adapters.SmallModuleClickListener
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.SmallModule
import isel.pt.unicommunity.viewmodel.BoardViewModel
import kotlinx.android.synthetic.main.fragment_all_boards.*

class BoardMenuFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_boards, container, false)
    }

    override fun onStart() {
        super.onStart()

        val viewModel = (activity as AppCompatActivity).getViewModel("boardID"){
            BoardViewModel(/*id!!*/)//TODO double bangs onde e que ha mesmo a verificaçao
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        val onBoardClickListener = object : SmallModuleClickListener {
            override fun onClickListener(smallModule: SmallModule?) {

                if(smallModule!=null)
                    (activity as MainActivity).navigateTo(
                        if(smallModule.type == "forum")
                            ForumFragment()
                        else
                            BlackBoardFragment()
                    )

                //Toast.makeText(activity, smallBoardItem?.title ?: "nullsmall board item", Toast.LENGTH_LONG).show()
            }
        }
        recyclerView.adapter = BoardMenuAdapter(viewModel, onBoardClickListener)

        viewModel.liveData.observe(
            this,
            Observer { recyclerView.adapter =  BoardMenuAdapter(viewModel, onBoardClickListener)}
        )
    }

}