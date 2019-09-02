package isel.pt.unicommunity.presentation.fragment.modules.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.R
import isel.pt.unicommunity.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleForumLink
import isel.pt.unicommunity.presentation.activity.BackStackManagingActivity
import isel.pt.unicommunity.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.adapter.GenericForumAdapter
import isel.pt.unicommunity.presentation.adapter.OnClickListener
import isel.pt.unicommunity.presentation.adapter.PartialForumItemView
import isel.pt.unicommunity.presentation.viewmodel.ForumViewModel
import kotlinx.android.synthetic.main.fragment_forum.*

class ForumFragment(private val forumLink: GetSingleForumLink) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_forum, container, false)


    override fun onStart() {
        super.onStart()
        val activity =  activity as BackStackManagingActivity
        val app = (activity as AppCompatActivity).getUniCommunityApp()

        val viewModel = (activity as AppCompatActivity).getViewModel("forum${forumLink.href}"){
            ForumViewModel(app, forumLink)
        }
        recyclerView_forum.layoutManager = LinearLayoutManager(activity)

        val progress = OptionalProgressBar(activity) {
            viewModel.getForum()
        }

        viewModel.forum.observe(
            this,
            ProgressObs(progress) {
                val createForumItemLink = it._links.createForumItemLink
                val forumAddItem = forum_additem

                if (createForumItemLink == null)
                    forumAddItem.isVisible = false
                else {
                    forumAddItem.isVisible = true
                    forumAddItem.setOnClickListener {
                        activity.navigateTo(CreateForumItemFragment(createForumItemLink))
                    }
                }

                viewModel.parseForumItems(it)
            },
            ProgressObs(progress) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }

        )


        val onForumItemClickListener = object : OnClickListener<PartialForumItemView> {
            override fun onClick(value: PartialForumItemView) {
                val singleForumItemLink = value.getSingleForumItemLink
                Toast.makeText(activity, singleForumItemLink.href, Toast.LENGTH_SHORT).show()
                activity.navigateTo(
                    ForumItemFragment(
                        singleForumItemLink
                    )
                )
            }
        }

        viewModel.forumItems.observe(
            this,
            ProgressObs(progress) {
                recyclerView_forum.adapter =
                    GenericForumAdapter(
                        it,
                        onForumItemClickListener
                    )
            },
            ProgressObs(progress) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )

    }




}