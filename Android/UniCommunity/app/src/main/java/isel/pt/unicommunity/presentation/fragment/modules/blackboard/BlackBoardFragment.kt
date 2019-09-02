package isel.pt.unicommunity.presentation.fragment.modules.blackboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.R
import isel.pt.unicommunity.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleBlackBoardLink
import isel.pt.unicommunity.presentation.activity.BackStackManagingActivity
import isel.pt.unicommunity.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.adapter.BlackBoardItemView
import isel.pt.unicommunity.presentation.adapter.GenericBlackBoardsAdapter
import isel.pt.unicommunity.presentation.adapter.OnClickListener
import isel.pt.unicommunity.presentation.viewmodel.BlackBoardViewModel
import kotlinx.android.synthetic.main.fragment_blackboard.*


class BlackBoardFragment(val blackBoardLink: GetSingleBlackBoardLink) : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_blackboard, container, false)



    override fun onStart() {
        super.onStart()

        val activity =  activity as BackStackManagingActivity
        val app = (activity as AppCompatActivity).getUniCommunityApp()
        val viewModel = (activity as AppCompatActivity).getViewModel("blackBoard${blackBoardLink.href}"){
            BlackBoardViewModel(app, blackBoardLink)
        }

        blackboard_recylcerview.layoutManager = LinearLayoutManager(activity)


        val progress = OptionalProgressBar(activity) {
            viewModel.getBlackBoard()
        }

        viewModel.blackBoardLd.observe(
            this,
            ProgressObs(progress) {

                val link = it._links.createBlackBoardItemLink
                if(link!=null) {
                    button15.setOnClickListener {
                        activity.navigateTo(CreateBlackBoardItemFragment(link))
                    }
                }

                val smallBlackBoardItems = it._embedded?.blackboardItems
                val multipleBlackBoardItemsLink = it._links.getMultipleBlackBoardItemsLink

                if(smallBlackBoardItems == null)
                    viewModel.getBlackBoardItems(multipleBlackBoardItemsLink)
                else
                    viewModel.fillWithEmbedded(smallBlackBoardItems, multipleBlackBoardItemsLink)

            },
            ProgressObs(progress) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )




        val onBlackBoardItemViewClickListener = object : OnClickListener<BlackBoardItemView>{
            override fun onClick(value: BlackBoardItemView) {
                Toast.makeText(activity, value.getSingleBlackBoardItemLink.href , Toast.LENGTH_LONG).show()
                activity.navigateTo(
                    BlackBoardItemFragment(
                        value.getSingleBlackBoardItemLink
                    )
                )
            }
        }


        viewModel.blackBoardItemsLd.observe(
            this,
            ProgressObs(progress) {
                blackboard_recylcerview.adapter = GenericBlackBoardsAdapter(it, onBlackBoardItemViewClickListener)
            },
            ProgressObs(progress) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )




    }



}

