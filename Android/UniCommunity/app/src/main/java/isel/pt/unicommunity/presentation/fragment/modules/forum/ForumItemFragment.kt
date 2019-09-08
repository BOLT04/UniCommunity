package isel.pt.unicommunity.presentation.fragment.modules.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleForumItemLink
import isel.pt.unicommunity.presentation.activity.BackStackManagingActivity
import isel.pt.unicommunity.presentation.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.fragment.modules.comment.MultipleCommentsFragment
import isel.pt.unicommunity.presentation.viewmodel.ForumItemViewModel
import kotlinx.android.synthetic.main.fragment_forum_item_md.*

class ForumItemFragment(private val singleForumItemLink: GetSingleForumItemLink) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_forum_item_md, container, false)


    override fun onStart() {
        super.onStart()

        val app = (activity as AppCompatActivity).getUniCommunityApp()
        val activity = activity as BackStackManagingActivity

        val viewModel = (activity as AppCompatActivity).getViewModel("forumitemFragment${singleForumItemLink.href}"){
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

                if(!it.anonymousPost) {
                    val user = it._embedded?.user
                    val name = user?.name
                    if(name != null)
                        forum_item_author.text = name
                    else{

                        val getSingleUserLink = user?.links?.self ?: it._links.getSingleUserLink
                        if(getSingleUserLink!=null) {
                            viewModel.fetchUser(getSingleUserLink)
                        }
                    }

                }
                else
                    forum_item_author.text = "Anonymus"

                forum_item_created_at.text = it.createdAt

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

        viewModel.userLd.observe(
            this,
            Observer { forum_item_author.text = it.name },
            Observer {  }

            )


    }


}