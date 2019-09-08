package isel.pt.unicommunity.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.links.GetSingleCommentLink
import kotlinx.android.synthetic.main.fragment_home.view.*

class PartialCommentView(
    val userName : String?,
    val content : String,
    val createdAt : String,
    val self : GetSingleCommentLink
)

class CommentsAdapter(
    list: List<PartialCommentView>
) : AbstractAdapter<PartialCommentView>(
    list,
    R.layout.item_comment,
    object : ViewHolderProvider<PartialCommentView>{
        override fun getInstance(view: ViewGroup)
            = CommentViewHolder(view)
    }
)

class CommentViewHolder(
    view: View
) : AbstractViewHolder<PartialCommentView>(view) {

    private val commentText: TextView = view.findViewById(R.id.comment_txt)
    private val commentUser: TextView = view.findViewById(R.id.comment_user)

    override fun bindToView(value: PartialCommentView) {
        commentText.text = value.content
        commentUser.text = value.userName ?: "Anonymous"
    }
}