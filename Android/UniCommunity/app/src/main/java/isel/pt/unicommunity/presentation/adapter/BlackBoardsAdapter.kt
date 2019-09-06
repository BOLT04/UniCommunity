package isel.pt.unicommunity.presentation.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.links.GetSingleBlackBoardItemLink

class BlackBoardItemView(
    val title : String,
    val date : String,
    val author : String,
    val getSingleBlackBoardItemLink: GetSingleBlackBoardItemLink
)


class BlackBoardsAdapter(
    blackboards: List<BlackBoardItemView>,
    private val onBlackBoardClickListener: OnClickListener<BlackBoardItemView>
): AbstractAdapter<BlackBoardItemView>(
    blackboards,
    R.layout.item_blackboard,
    object : ViewHolderProvider<BlackBoardItemView>{
        override fun getInstance(
            view: ViewGroup
        )= BlackBoardVH(view,onBlackBoardClickListener)
    }
)

class BlackBoardVH(
    view: ViewGroup,
    private val onClickListener: OnClickListener<BlackBoardItemView>
) : AbstractViewHolder<BlackBoardItemView>(view) {

    val title : TextView = view.findViewById(R.id.title)
    val autor : TextView = view.findViewById(R.id.author)
    val date : TextView = view.findViewById(R.id.date)


    val layout : ConstraintLayout = view.findViewById(R.id.blackboardItem_layout)

    override fun bindToView(value: BlackBoardItemView) {
        title.text= value.title
        autor.text= value.author
        date.text = value.date

        layout.setOnClickListener{
            onClickListener.onClick(value)
        }
    }

}
