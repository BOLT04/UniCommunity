package isel.pt.unicommunity.presentation.adapter.old
/*
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.presentation.viewmodel.ForumViewModel

class ForumAdapter(
    private val allForumItems: List<ForumViewModel.PartialForumItemView>,
    private val onForumItemClickListener: PartialForumItemClickListener

): RecyclerView.Adapter<ForumItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumItemViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_forum_item, parent, false) as ViewGroup

        return ForumItemViewHolder(
            view,
            onForumItemClickListener
        )
    }

    override fun getItemCount(): Int =
        allForumItems.size


    override fun onBindViewHolder(holder: ForumItemViewHolder, position: Int) {
        holder.bindToView(allForumItems[position])
    }
}

interface PartialForumItemClickListener {
    fun onClickListener(partialForumItemView : ForumViewModel.PartialForumItemView)
}

class ForumItemViewHolder(
    view: ViewGroup,
    val onForumItemClickListener: PartialForumItemClickListener
): RecyclerView.ViewHolder(view) {

    val name : TextView = view.findViewById(R.id.forum_item_name)
    val layout : ConstraintLayout = view.findViewById(R.id.constraint_layout)

    fun bindToView(partialForumItemView: ForumViewModel.PartialForumItemView){

        name.text = partialForumItemView.name

        layout.setOnClickListener{onForumItemClickListener.onClickListener(partialForumItemView)}
    }

}*/