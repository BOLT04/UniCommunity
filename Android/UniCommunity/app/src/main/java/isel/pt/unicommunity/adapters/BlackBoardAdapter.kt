package isel.pt.unicommunity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.SmallBlackBoardItem
import isel.pt.unicommunity.viewmodel.BlackBoardViewModel


class BlackBoardAdapter(
    val viewModel: BlackBoardViewModel

) : RecyclerView.Adapter<SmallBlackBoardItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallBlackBoardItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.blackboard_item_layout, parent, false) as ViewGroup

        return SmallBlackBoardItemViewHolder(view)
    }


    override fun getItemCount(): Int =
        viewModel.liveData.value?.size ?: 0



    override fun onBindViewHolder(holder: SmallBlackBoardItemViewHolder, position: Int) {
        holder.bindToView(viewModel.liveData.value?.get(position))
    }

}

class SmallBlackBoardItemViewHolder(view :ViewGroup) : RecyclerView.ViewHolder(view) {

    val title : TextView = view.findViewById(R.id.title)
    val autor : TextView = view.findViewById(R.id.author)
    val date : TextView = view.findViewById(R.id.date)
    val smallDesc : TextView = view.findViewById(R.id.small_desc)

    fun bindToView(smallBlackBoardItem: SmallBlackBoardItem?){
        title.text= smallBlackBoardItem?.title ?: "empty"
        autor.text= smallBlackBoardItem?.author ?: "empty"
        date.text= smallBlackBoardItem?.date?.toString() ?: "empty"
        smallDesc.text= smallBlackBoardItem?.desc ?: "empty"

    }

}
