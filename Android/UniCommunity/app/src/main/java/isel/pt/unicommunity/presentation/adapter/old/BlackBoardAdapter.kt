package isel.pt.unicommunity.presentation.adapter.old
/*
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.viewmodel.BlackBoardViewModel


class BlackBoardAdapter(
    val blackBoardItems: List<BlackBoardViewModel.BlackBoardItemView>,
    val onBlackBoardItemViewClickListener : OnBlackBoardItemViewClickListener
) : RecyclerView.Adapter<BlackBoardItemReprViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlackBoardItemReprViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_blackboard, parent, false) as ViewGroup

        return BlackBoardItemReprViewHolder(view, onBlackBoardItemViewClickListener)
    }


    override fun getItemCount(): Int =
        blackBoardItems.size



    override fun onBindViewHolder(holder: BlackBoardItemReprViewHolder, position: Int) {
        holder.bindToView(blackBoardItems[position])
    }

}

interface OnBlackBoardItemViewClickListener {
    fun onClick(item : BlackBoardViewModel.BlackBoardItemView)
}

class BlackBoardItemReprViewHolder(
    view :ViewGroup,
    val onBlackBoardItemViewClickListener: OnBlackBoardItemViewClickListener
) : RecyclerView.ViewHolder(view) {

    val title : TextView = view.findViewById(R.id.title)
    val autor : TextView = view.findViewById(R.id.author)
    val date : TextView = view.findViewById(R.id.date)


    val layout :  ConstraintLayout = view.findViewById(R.id.blackboardItem_layout)

    fun bindToView(blackBoardItemRepr: BlackBoardViewModel.BlackBoardItemView){
        title.text= blackBoardItemRepr.title
        autor.text=blackBoardItemRepr.author
        date.text = blackBoardItemRepr.date

        layout.setOnClickListener{
            onBlackBoardItemViewClickListener.onClick(blackBoardItemRepr)
        }

    }

}
*/