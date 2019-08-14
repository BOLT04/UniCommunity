package isel.pt.unicommunity.presentation.fragment.modules.blackboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import isel.pt.unicommunity.presentation.activity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleBlackBoardItemLink
import isel.pt.unicommunity.presentation.viewmodel.BlackBoardItemViewModel
import kotlinx.android.synthetic.main.fragment_blackboard_item_md.*

class BlackBoardItemFragment(val link : GetSingleBlackBoardItemLink) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_blackboard_item_md, container, false)


    override fun onStart() {
        super.onStart()
        val activity =  activity as MainActivity
        val app = activity.getUniCommunityApp()

        val viewModel = (activity as AppCompatActivity).getViewModel("blackBoard${link.href}"){
            BlackBoardItemViewModel(app, link)
        }

        viewModel.getBlackBoardItem()

        viewModel.blackboardItem.observe(this, Observer {

            title.text = it.name
            blackboard_item_content_md.loadMarkdown(it.content)

            /*blackboard_item_authorname.text = it.authorName
            blackboard_item_name.text = it.name
            blackboard_item_createdat.text = it.createdAt
            blackboard_item_content.text = it.content ?: ""*/


        })


    }


}