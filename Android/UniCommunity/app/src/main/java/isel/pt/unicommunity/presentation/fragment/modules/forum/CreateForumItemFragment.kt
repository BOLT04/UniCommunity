package isel.pt.unicommunity.presentation.fragment.modules.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.CreateForumItemLink
import isel.pt.unicommunity.presentation.viewmodel.CreateForumItemViewModel
import kotlinx.android.synthetic.main.__testing__demo.*
import kotlinx.android.synthetic.main.fragment_create_forum_item.*

class CreateForumItemFragment(val createForumItemLink: CreateForumItemLink) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_create_forum_item, container, false)


    override fun onStart() {
        super.onStart()

        val activity = activity as AppCompatActivity
        val app = activity.getUniCommunityApp()

        val viewModel = activity.getViewModel("CreateForumItem${createForumItemLink.href}"){
            CreateForumItemViewModel(app, createForumItemLink)
        }
        create_forum_item_button.setOnClickListener {
            viewModel.createForumItem(
                title.text.toString(),
                content.text.toString(),
                anonymus.isChecked
            )
        }

        viewModel.createForumItemLd.observe(
            this,
            Observer {
                Toast.makeText(activity, "Posted with success", Toast.LENGTH_SHORT).show()
            },
            Observer {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )

    }


}