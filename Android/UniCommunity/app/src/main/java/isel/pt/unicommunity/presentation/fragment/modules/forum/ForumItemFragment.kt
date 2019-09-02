package isel.pt.unicommunity.presentation.fragment.modules.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import isel.pt.unicommunity.R
import isel.pt.unicommunity.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleForumItemLink
import isel.pt.unicommunity.presentation.activity.BackStackManagingActivity
import isel.pt.unicommunity.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.fragment.modules.comment.MultipleCommentsFragment
import isel.pt.unicommunity.presentation.viewmodel.ForumItemViewModel
import kotlinx.android.synthetic.main.fragment_forum_item_md.*

class ForumItemFragment(val singleForumItemLink: GetSingleForumItemLink) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_forum_item_md, container, false)


    override fun onStart() {
        super.onStart()

        val app = (activity as AppCompatActivity).getUniCommunityApp()
        val activity = activity as BackStackManagingActivity

        val viewModel = (activity as AppCompatActivity).getViewModel("blackBoard"){
            ForumItemViewModel(app, singleForumItemLink)
        }

        val progress = OptionalProgressBar(activity) {
            viewModel.fetchForumItem()
        }


        viewModel.forumItemLd.observe(
            this,
            ProgressObs(progress) {

                forum_item_title.text = it.name
                forum_item_content_md.loadMarkdown(it.content)


                forum_item_comments_btn.isVisible = false

                val multipleCommentsLink = it._links.getMultipleCommentsLink
                if(multipleCommentsLink!=null){
                    forum_item_comments_btn.isVisible = true
                    forum_item_comments_btn.setOnClickListener {
                        activity.navigateTo(MultipleCommentsFragment(multipleCommentsLink))
                    }
                }

            },
            ProgressObs(progress) { Toast.makeText(activity, it, Toast.LENGTH_SHORT).show() }
        )


    }


}