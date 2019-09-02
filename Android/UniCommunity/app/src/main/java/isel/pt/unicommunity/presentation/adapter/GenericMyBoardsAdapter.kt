package isel.pt.unicommunity.presentation.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import isel.pt.unicommunity.R

open class GenericMyBoardsAdapter(
    allBoards: List<PartialBoardView>,
    private val onBoardClickListener: OnClickListener<PartialBoardView>
): AbstractAdapter<PartialBoardView>(
    allBoards,
    R.layout.item_all_boards,
    object : ViewHolderProvider<PartialBoardView>{
        override fun getInstance(
            view: ViewGroup
        )= GenericMyBoardsVH(view,onBoardClickListener)
    }
)


class GenericMyBoardsVH(
    view: ViewGroup,
    private val onBoardClickListener: OnClickListener<PartialBoardView>
) : AbstractViewHolder<PartialBoardView>(view) {

    val name : TextView = view.findViewById(R.id.title_sb)
    val desc : TextView = view.findViewById(R.id.desc_sb)
    val layout : ConstraintLayout = view.findViewById(R.id.constraint_layout)

    override fun bindToView(value: PartialBoardView) {
        name.text = value.name
        value.description?.let{desc.text = it}

        layout.setOnClickListener{onBoardClickListener.onClick(value)}
    }

}