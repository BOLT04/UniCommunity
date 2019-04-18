package isel.pt.unicommunity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.SmallBoardItem
import isel.pt.unicommunity.viewmodel.BoardsViewModel
import isel.pt.unicommunity.viewmodel.MyBoardsViewModel


class MyBoardsAdapter(
    private val viewModel: MyBoardsViewModel,
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


//TODO holder diferenete se tiver outro tipo de vista