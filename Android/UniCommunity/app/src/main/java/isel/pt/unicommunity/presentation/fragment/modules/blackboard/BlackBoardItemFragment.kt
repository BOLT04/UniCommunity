package isel.pt.unicommunity.presentation.fragment.modules.blackboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleBlackBoardItemLink
import isel.pt.unicommunity.presentation.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.viewmodel.BlackBoardItemViewModel
import kotlinx.android.synthetic.main.fragment_blackboard_item_md.*

class BlackBoardItemFragment(val link : GetSingleBlackBoardItemLink) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_blackboard_item_md, container, false)


    override fun onStart() {
        super.onStart()
        val activity =  activity as AppCompatActivity
        val app = activity.getUniCommunityApp()

        val viewModel = activity.getViewModel("blackBoard${link.href}"){
            BlackBoardItemViewModel(app, link)
        }

        val progress = OptionalProgressBar(activity) {
            viewModel.getBlackBoardItem()
        }

        viewModel.blackboardItem.observe(
            this,
            ProgressObs(progress) {
                title.text = it.name
                blackboard_item_content_md.loadMarkdown(it.content)
            },
            ProgressObs(progress) { Toast.makeText(activity, it, Toast.LENGTH_SHORT).show() }
        )


    }


}