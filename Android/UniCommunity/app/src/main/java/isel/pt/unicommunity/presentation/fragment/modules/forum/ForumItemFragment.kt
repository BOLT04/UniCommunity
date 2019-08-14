package isel.pt.unicommunity.presentation.fragment.modules.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleForumItemLink
import isel.pt.unicommunity.presentation.viewmodel.ForumItemViewModel

class ForumItemFragment(val singleForumItemLink: GetSingleForumItemLink) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_forum_item, container, false)


    override fun onStart() {
        super.onStart()

        val viewModel = (activity as AppCompatActivity).getViewModel("blackBoard"){
            ForumItemViewModel()
        }




    }


}