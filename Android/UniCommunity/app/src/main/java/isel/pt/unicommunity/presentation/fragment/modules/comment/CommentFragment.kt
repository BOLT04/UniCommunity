package isel.pt.unicommunity.presentation.fragment.modules.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import isel.pt.unicommunity.R
import isel.pt.unicommunity.kotlinx.getViewModel
import isel.pt.unicommunity.model.links.GetSingleCommentLink
import isel.pt.unicommunity.presentation.viewmodel.CommentViewModel

class CommentFragment(val getSingleCommentLink: GetSingleCommentLink) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_comment, container, false)


}