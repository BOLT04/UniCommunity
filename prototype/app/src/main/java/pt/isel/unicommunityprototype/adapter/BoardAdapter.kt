package pt.isel.unicommunityprototype.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.model.BlackBoardItem
import pt.isel.unicommunityprototype.model.BoardElement
import pt.isel.unicommunityprototype.model.ForumItem
import pt.isel.unicommunityprototype.model.Header
import pt.isel.unicommunityprototype.viewmodel.BoardViewModel

class BoardAdapter(private val viewModel: BoardViewModel,
                   private val listener: OnBoardElemClickListener
) : RecyclerView.Adapter<BoardElemViewHolder>() {

    override fun getItemCount() = viewModel.elements.value?.size ?: 0

    val HEADER = 1
    val BLACKBOARD_ITEM = 2
    val FORUM_ITEM = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardElemViewHolder {

            when(viewType){
                HEADER -> return HeaderViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.module_header, parent, false))
                BLACKBOARD_ITEM -> return BlackBoardItemViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.blackboard_item, parent, false))
                FORUM_ITEM -> return ForumItemViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.forum_item, parent, false))
                else -> throw RuntimeException("NOT A VALID MODULE") //todo can we optimize the switch?
            }


    }

    override fun getItemViewType(position: Int) =
        when(viewModel.elements.value?.get(position)){
            is Header -> HEADER
            is BlackBoardItem -> BLACKBOARD_ITEM
            is ForumItem -> FORUM_ITEM
            else -> throw RuntimeException("NOT A VALID MODULE") //todo
        }

    override fun onBindViewHolder(holder: BoardElemViewHolder, position: Int) {
        //TODO: get() same as elementAt()? holder.bindTo(viewModel.teams.value?.get(position), listener)
        holder.bindTo(viewModel.elements.value?.elementAt(position), listener)
    }

    interface OnBoardElemClickListener {
        fun onBoardElemClick(elem: BoardElement?)
    }
}

abstract class BoardElemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bindTo(elem: BoardElement?, listener: BoardAdapter.OnBoardElemClickListener)
}

class HeaderViewHolder(view: View) : BoardElemViewHolder(view){
    val title = view.findViewById<TextView>(R.id.title)

    override fun bindTo(elem: BoardElement?, listener: BoardAdapter.OnBoardElemClickListener) {
        val header: Header = elem as Header
        title.text=header.title
    }
}

class BlackBoardItemViewHolder(view: View) : BoardElemViewHolder(view){
    val content = view.findViewById<TextView>(R.id.content)

    override fun bindTo(elem: BoardElement?, listener: BoardAdapter.OnBoardElemClickListener) {
        val blackboardItem: BlackBoardItem = elem as BlackBoardItem
        content.text = blackboardItem.desc
    }
}

class ForumItemViewHolder(view: View) : BoardElemViewHolder(view){
    val title = view.findViewById<TextView>(R.id.title)
    val content  = view.findViewById<TextView>(R.id.content)
    val checkbox = view.findViewById<CheckBox>(R.id.checkBox)


    override fun bindTo(elem: BoardElement?, listener: BoardAdapter.OnBoardElemClickListener) {
        val forumItem = elem as ForumItem

        content.text = forumItem.desc


    }
}