package isel.pt.unicommunity.presentation.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import isel.pt.unicommunity.R

open class MyBoardsAdapter(
    allBoards: List<PartialBoardView>,
    private val onBoardClickListener: OnClickListener<PartialBoardView>
): AbstractAdapter<PartialBoardView>(
    allBoards,
    R.layout.item_my_boards,
    object : ViewHolderProvider<PartialBoardView>{
        override fun getInstance(
            view: ViewGroup
        )= MyBoardsVH(view,onBoardClickListener)
    }
)


class MyBoardsVH(
    view: ViewGroup,
    private val onBoardClickListener: OnClickListener<PartialBoardView>
) : AbstractViewHolder<PartialBoardView>(view) {

    val name : TextView = view.findViewById(R.id.title_sb)
    val layout : ConstraintLayout = view.findViewById(R.id.constraint_layout)

    override fun bindToView(value: PartialBoardView) {
        name.text = value.name

        layout.setOnClickListener{onBoardClickListener.onClick(value)}
    }

}