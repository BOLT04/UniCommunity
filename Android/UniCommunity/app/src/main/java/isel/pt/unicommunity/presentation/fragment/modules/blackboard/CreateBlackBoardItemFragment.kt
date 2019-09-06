package isel.pt.unicommunity.presentation.fragment.modules.blackboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import isel.pt.unicommunity.presentation.activity.MainActivity
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.CreateBlackBoardItemLink
import isel.pt.unicommunity.presentation.viewmodel.CreateBlackBoardItemViewModel
import kotlinx.android.synthetic.main.fragment_create_blackboard_item.*


class CreateBlackBoardItemFragment(val link : CreateBlackBoardItemLink) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_create_blackboard_item, container, false)


    override fun onStart() {
        super.onStart()
        val activity =  activity as AppCompatActivity
        val app = activity.getUniCommunityApp()

        val viewModel = activity.getViewModel("blackBoard${link.href}"){
            CreateBlackBoardItemViewModel(app, link)
        }

        create_forum_item_button.setOnClickListener {

            val title = checkInput(title, false)

            if(title==null){
                Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val content = checkInput(content, true)

            viewModel.postItem(
                title,
                content
            )

            viewModel.createItemLd.observe(
                activity,
                Observer {
                    Toast.makeText(activity, "Posted with success", Toast.LENGTH_SHORT).show()
                },
                Observer {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
            )
        }

    }

    private fun checkInput(field: TextView, canBeEmpty: Boolean): String? {
        val value = field.text.toString()

        if(canBeEmpty || (value.isNotBlank() && value.isNotEmpty()))
            return value

        return null
    }


}