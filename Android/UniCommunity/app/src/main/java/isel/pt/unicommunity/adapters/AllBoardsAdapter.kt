package isel.pt.unicommunity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.SmallBoardItem
import isel.pt.unicommunity.viewmodel.AllBoardsViewModel
import isel.pt.unicommunity.viewmodel.BoardsViewModel


class AllBoardsAdapter(
    private val viewModel: AllBoardsViewModel,
    private val onBoardClickListener: BoardClickListener
): RecyclerView.Adapter<AllBoardsItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBoardsItemViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_all_boards, parent, false) as ViewGroup

        return AllBoardsItemViewHolder(view, onBoardClickListener)
    }

    override fun getItemCount(): Int =
        viewModel.liveData.value?.size ?: 0


    override fun onBindViewHolder(holder: AllBoardsItemViewHolder, position: Int) {
        holder.bindToView(viewModel.liveData.value?.get(position))
    }
}

interface BoardClickListener {

    fun onClickListener(smallBoardItem : SmallBoardItem?)
}

class AllBoardsItemViewHolder(
    view: ViewGroup,
    val onBoardClickListener: BoardClickListener
): RecyclerView.ViewHolder(view) {

    val name : TextView = view.findViewById(R.id.title_sb)
    val desc : TextView = view.findViewById(R.id.desc_sb)
    val layout : ConstraintLayout = view.findViewById(R.id.constraint_layout)

    fun bindToView(smallBoardItem : SmallBoardItem?){

        name.text = smallBoardItem?.name ?: "no name"
        desc.text = smallBoardItem?.desc ?: "no desc" //todo seems shady

        layout.setOnClickListener{onBoardClickListener.onClickListener(smallBoardItem)}
    }

}
