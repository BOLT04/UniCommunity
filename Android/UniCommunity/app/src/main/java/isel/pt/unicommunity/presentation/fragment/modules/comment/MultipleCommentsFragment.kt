package isel.pt.unicommunity.presentation.fragment.modules.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.common.OptionalProgressBar
import isel.pt.unicommunity.presentation.common.ProgressObs
import isel.pt.unicommunity.kotlinx.getUniCommunityApp
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetMultipleCommentsLink
import isel.pt.unicommunity.presentation.adapter.CommentsAdapter
import isel.pt.unicommunity.presentation.adapter.OnClickListener
import isel.pt.unicommunity.presentation.adapter.PartialCommentView
import isel.pt.unicommunity.presentation.viewmodel.CommentViewModel
import kotlinx.android.synthetic.main.fragment_all_comments.*

class MultipleCommentsFragment(private val getMultipleCommentsLink: GetMultipleCommentsLink) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_all_comments, container, false)


    override fun onStart() {
        super.onStart()

        val appCompatActivity = activity as AppCompatActivity
        val app = appCompatActivity.getUniCommunityApp()


        val viewModel = appCompatActivity
            .getViewModel("comments${getMultipleCommentsLink.href}"){
                CommentViewModel(app, getMultipleCommentsLink)
        }

        comments_rv.layoutManager = LinearLayoutManager(appCompatActivity)


        val progressBar = OptionalProgressBar(appCompatActivity){
            viewModel.fetchComments()
        }



        viewModel.comments.observe(
            this,
            ProgressObs(progressBar){

                comments_rv.adapter = CommentsAdapter(it)

            },
            ProgressObs(progressBar){
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )




    }


}