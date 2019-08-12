package isel.pt.unicommunity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.to_refactor.small.SmallBoardItem
import isel.pt.unicommunity.model.webdto.old.board.AllBoardsDto
import isel.pt.unicommunity.model.webdto.rel_links.PartialBoardItem
import isel.pt.unicommunity.presentation.viewmodel.AllBoardsViewModel


class AllBoardsAdapter(
    private val allBoards: List<PartialBoardItem>,
    private val onBoardClickListener: PartialBoardItemClickListener

): RecyclerView.Adapter<AllBoardsItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBoardsItemViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_all_boards, parent, false) as ViewGroup

        return AllBoardsItemViewHolder(view, onBoardClickListener)
    }

    override fun getItemCount(): Int =
        allBoards.size


    override fun onBindViewHolder(holder: AllBoardsItemViewHolder, position: Int) {
        holder.bindToView(allBoards[position])
    }
}

interface PartialBoardItemClickListener {
    fun onClickListener(partialBoardIem : PartialBoardItem)
}

class AllBoardsItemViewHolder(
    view: ViewGroup,
    val onBoardClickListener: PartialBoardItemClickListener
): RecyclerView.ViewHolder(view) {

    val name : TextView = view.findViewById(R.id.title_sb)
    val desc : TextView = view.findViewById(R.id.desc_sb)
    val layout : ConstraintLayout = view.findViewById(R.id.constraint_layout)

    fun bindToView(partialBoardIem: PartialBoardItem){

        name.text = partialBoardIem.name
        desc.text = partialBoardIem.description ?: "no desc" //todo seems shady

        layout.setOnClickListener{onBoardClickListener.onClickListener(partialBoardIem)}
    }

}
