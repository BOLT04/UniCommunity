package isel.pt.unicommunity.presentation.adapter.old

/*import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.collectionjson.BoardCollection

open class AllBoardsAdapter(
    private val allBoards: List<BoardCollection.PartialBoard>,
    private val onBoardClickListener: OnClickListener<BoardCollection.PartialBoard>
    //private val context: Context?

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
    fun onClickListener(partialBoardIem : BoardCollection.PartialBoard)
}




class AllBoardsItemViewHolder(
    view: ViewGroup,
    val onBoardClickListener: OnClickListener<BoardCollection.PartialBoard>
): RecyclerView.ViewHolder(view) {

    val name : TextView = view.findViewById(R.id.title_sb)
    val desc : TextView = view.findViewById(R.id.desc_sb)
    //val subBtn : Button = view.findViewById(R.id.subBtn)
    val layout : ConstraintLayout = view.findViewById(R.id.constraint_layout)

    fun bindToView(item: BoardCollection.PartialBoard){

        name.text = item.name
        item.description?.let{desc.text = it}

        /*subBtn.setOnClickListener {
            // TODO: this logic should be moved to myholder class that interfaces with the FCM API
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/1-1-Anuncios")// se n funcionar tentar: /topics/1-1-Anuncios
                .addOnCompleteListener { task ->
                    var msg = "subbed yh"//getString(R.string.msg_subscribed)
                    if (!task.isSuccessful) {
                        msg = "not subbed bruv"//getString(R.string.msg_subscribe_failed)
                    }
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
        }*/


        layout.setOnClickListener{onBoardClickListener.onClick(item)}
    }

}*/