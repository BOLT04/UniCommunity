package isel.pt.unicommunity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.viewmodel.BlackBoardViewModel


class BlackBoardAdapter(
    val blackBoardItems: List<BlackBoardViewModel.BlackBoardItemRepr>,
    val onBlackBoardItemReprClickListener : OnBlackBoardItemReprClickListener
) : RecyclerView.Adapter<BlackBoardItemReprViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlackBoardItemReprViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_blackboard, parent, false) as ViewGroup

        return BlackBoardItemReprViewHolder(view, onBlackBoardItemReprClickListener)
    }


    override fun getItemCount(): Int =
        blackBoardItems.size



    override fun onBindViewHolder(holder: BlackBoardItemReprViewHolder, position: Int) {
        holder.bindToView(blackBoardItems[position])
    }

}

interface OnBlackBoardItemReprClickListener {
    fun onClick(item : BlackBoardViewModel.BlackBoardItemRepr)
}

class BlackBoardItemReprViewHolder(
    view :ViewGroup,
    val onBlackBoardItemReprClickListener: OnBlackBoardItemReprClickListener
) : RecyclerView.ViewHolder(view) {

    val title : TextView = view.findViewById(R.id.title)
    val autor : TextView = view.findViewById(R.id.author)
    val date : TextView = view.findViewById(R.id.date)


    val layout :  ConstraintLayout = view.findViewById(R.id.blackboardItem_layout)

    fun bindToView(blackBoardItemRepr: BlackBoardViewModel.BlackBoardItemRepr){
        title.text= blackBoardItemRepr.title
        autor.text=blackBoardItemRepr.author
        date.text = blackBoardItemRepr.date
        /*autor.text= blackBoardItemRepr?.author ?: "empty"
        date.text= blackBoardItemRepr?.date?.toString() ?: "empty"
        smallDesc.text= blackBoardItemRepr?.desc ?: "empty" todo */

        layout.setOnClickListener{
            onBlackBoardItemReprClickListener.onClick(blackBoardItemRepr)
        }

    }

}
