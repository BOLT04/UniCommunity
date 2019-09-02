package isel.pt.unicommunity.presentation.adapter

import android.view.View
import android.view.ViewGroup
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.links.GetSingleCommentLink

class PartialCommentView(
    val userName : String?,
    val content : String,
    val createdAt : String,
    val self : GetSingleCommentLink
)

class GenericCommentsAdapter(
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


    override fun bindToView(value: PartialCommentView) {
        //todo
    }
}