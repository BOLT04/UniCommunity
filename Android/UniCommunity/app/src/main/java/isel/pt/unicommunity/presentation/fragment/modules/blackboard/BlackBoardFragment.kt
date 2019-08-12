package isel.pt.unicommunity.presentation.fragment.modules.blackboard

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
import isel.pt.unicommunity.presentation.adapter.BlackBoardAdapter
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.webdto.rel_links.GetSingleBlackBoardLink
import isel.pt.unicommunity.presentation.adapter.OnBlackBoardItemReprClickListener
import isel.pt.unicommunity.presentation.viewmodel.BlackBoardItemRepr
import isel.pt.unicommunity.presentation.viewmodel.BlackBoardViewModel
import kotlinx.android.synthetic.main.fragment_blackboard.*


class BlackBoardFragment(val blackBoardLink: GetSingleBlackBoardLink) : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_blackboard, container, false)



    override fun onStart() {
        super.onStart()

        val activity =  activity as MainActivity
        val app = activity.getUniCommunityApp()
        val viewModel = (activity as AppCompatActivity).getViewModel("blackBoard${blackBoardLink.href}"){
            BlackBoardViewModel(app, blackBoardLink)
        }

        blackboard_recylcerview.layoutManager = LinearLayoutManager(activity)



        viewModel.getBlackBoard()

        viewModel.blackBoard.observe(this, Observer {

            val link = it._links.createBlackBoardItemLink
            if(link!=null) {
                button15.setOnClickListener {
                    activity.navigateTo(CreateBlackBoardItemFragment(link))
                }
            }

            //todo verificaçao se vem no embedded e se nao fazer o novo pedido
            val multipleBlackBoardItemsLink = it._links.getMultipleBlackBoardItemsLink
            if(multipleBlackBoardItemsLink != null)
                viewModel.getBlackBoardItems(multipleBlackBoardItemsLink)
        })


        val onBlackBoardItemReprClickListener = object : OnBlackBoardItemReprClickListener{
            override fun onClick(item: BlackBoardItemRepr) {
                Toast.makeText(activity, item.getSingleBlackBoardItemLink.href , Toast.LENGTH_LONG).show()
                activity.navigateTo(
                    BlackBoardItemFragment(
                        item.getSingleBlackBoardItemLink
                    )
                )
            }
        }

        viewModel.blackBoardItems.observe(this, Observer {

            blackboard_recylcerview.adapter = BlackBoardAdapter(it, onBlackBoardItemReprClickListener)

        })

        /*val onBoardClickListener = object : BoardClickListener {
            override fun onClickListener(smallBoardItem: SmallBoardItem?) {

                (activity as MainActivity).navigateTo(BoardMenuFragment())

                Toast.makeText(activity, smallBoardItem?.APP_NAME ?: "nullsmall board item", Toast.LENGTH_LONG).show()
            }
        }*/


    }



}