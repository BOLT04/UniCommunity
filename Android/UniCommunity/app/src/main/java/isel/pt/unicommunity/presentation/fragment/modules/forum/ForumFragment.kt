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
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.presentation.activity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleForumLink
import isel.pt.unicommunity.presentation.adapter.ForumAdapter
import isel.pt.unicommunity.presentation.adapter.PartialForumItemClickListener
import isel.pt.unicommunity.presentation.viewmodel.ForumViewModel
import kotlinx.android.synthetic.main.fragment_forum.*

class ForumFragment(private val forumLink: GetSingleForumLink) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_forum, container, false)


    override fun onStart() {
        super.onStart()
        val activity =  activity as MainActivity
        val app = activity.getUniCommunityApp()

        val viewModel = (activity as AppCompatActivity).getViewModel("blackBoard"){
            ForumViewModel(app, forumLink)
        }
        recyclerView2.layoutManager = LinearLayoutManager(activity)

        viewModel.getForum()

        viewModel.forum.observe(this, Observer {

            val createForumItemLink = it._links.createForumItemLink
            val forumAdditem = forum_additem

            if(createForumItemLink == null)
                forumAdditem.isVisible = false
            else {
                forumAdditem.isVisible = true
                forumAdditem.setOnClickListener {
                    //todo criar anuncio activity.navigateTo()
                }
            }

            viewModel.parseForumItems(it)

        })


        val onForumItemClickListener = object : PartialForumItemClickListener {
            override fun onClickListener(partialForumItemView: ForumViewModel.PartialForumItemView) {
                val singleForumItemLink = partialForumItemView.getSingleForumItemLink
                Toast.makeText(activity, singleForumItemLink.href, Toast.LENGTH_SHORT).show()
                activity.navigateTo(
                    ForumItemFragment(
                        singleForumItemLink
                    )
                )
            }

        }

        viewModel.forumItems.observe(this, Observer {
            recyclerView2.adapter = ForumAdapter(it, onForumItemClickListener)
        })

    }




}