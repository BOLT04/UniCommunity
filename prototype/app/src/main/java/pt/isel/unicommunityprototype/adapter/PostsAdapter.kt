package pt.isel.unicommunityprototype.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pt.isel.unicommunityprototype.model.Board //TODO: are dependencies correct: Adapter (Presentation) -> data layer
import pt.isel.unicommunityprototype.model.Post
import pt.isel.unicommunityprototype.viewmodel.UserPanelViewModel

//TODO: not finished
class PostsAdapter(
    private val viewModel: UserPanelViewModel,
    private val listener: OnPostClickListener
) : RecyclerView.Adapter<BoardsViewHolder>() {

    override fun getItemCount() = viewModel.boards.value?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardsViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false) as View

        return BoardsViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardsViewHolder, position: Int) {
        //TODO: get() same as elementAt()? holder.bindTo(viewModel.teams.value?.get(position), listener)
        //holder.bindTo(viewModel.boards.value?.elementAt(position), listener)
    }

    interface OnPostClickListener {
        fun onPostClick(post: Post?)
    }
}
/*
class BoardsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val boardNameView: TextView = view.findViewById(android.R.id.text1)

    fun bindTo(board: Board?, listener: BoardsAdapter.OnBoardClickListener) {
        boardNameView.text = board?.name
        itemView.setOnClickListener { listener.onBoardClick(board) }
    }
}
*/