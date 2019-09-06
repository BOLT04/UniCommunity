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
    list: List<PartialCommentView>,
    onClickListener: OnClickListener<PartialCommentView>
) : AbstractAdapter<PartialCommentView>(
    list,
    R.layout.item_comment,
    object : ViewHolderProvider<PartialCommentView>{
        override fun getInstance(view: ViewGroup)
            = CommentViewHolder(view, onClickListener)
    }
)

class CommentViewHolder(
    view: View,
    onClickListener: OnClickListener<PartialCommentView>
) : AbstractViewHolder<PartialCommentView>(view) {

    val commentText = view.findViewById<TextView>(R.id.comment_txt)
    val commentUser = view.findViewById<TextView>(R.id.comment_user)

    override fun bindToView(value: PartialCommentView) {
        commentText.text = value.content
        commentUser.text = value.userName ?: "Anonymus"
    }
}