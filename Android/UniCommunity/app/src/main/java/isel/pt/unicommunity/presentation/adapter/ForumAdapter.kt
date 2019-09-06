package isel.pt.unicommunity.presentation.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.links.GetSingleForumItemLink

class PartialForumItemView(
    val name: String,
    val author : String?,
    val getSingleForumItemLink: GetSingleForumItemLink
)


class ForumAdapter(
    partialForumItemView: List<PartialForumItemView>,
    private val onClickListener: OnClickListener<PartialForumItemView>
): AbstractAdapter<PartialForumItemView>(
    partialForumItemView,
    R.layout.item_forum_item,
    object : ViewHolderProvider<PartialForumItemView>{
        override fun getInstance(view: ViewGroup)
            = ForumItemViewHolder(view, onClickListener)
    }
)


class ForumItemViewHolder(
    view: ViewGroup,
    private val onForumItemClickListener: OnClickListener<PartialForumItemView>
): AbstractViewHolder<PartialForumItemView>(view) {
    override fun bindToView(value: PartialForumItemView) {
        name.text = value.name

        layout.setOnClickListener{onForumItemClickListener.onClick(value)}
    }

    val name : TextView = view.findViewById(R.id.forum_item_name)
    val layout : ConstraintLayout = view.findViewById(R.id.constraint_layout)

}