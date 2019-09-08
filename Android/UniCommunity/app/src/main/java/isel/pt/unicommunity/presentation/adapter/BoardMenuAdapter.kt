package isel.pt.unicommunity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.links.GetSingleBlackBoardLink
import isel.pt.unicommunity.model.links.GetSingleForumLink
import isel.pt.unicommunity.presentation.viewmodel.BoardViewModel


class MenuView(
    val blackBoards : List<BoardViewModel.PartialBlackBoardView>,
    val forum : GetSingleForumLink?
)


class BoardMenuAdapter(
    menu: MenuView,
    private val onBlackBoardClickListener: BlackBoardClickListener,
    private val onForumClickListener : ForumClickListener
): RecyclerView.Adapter<ModuleViewHolder>()
{
    class Module(
        val title: String,
        val onClickListener: () -> Unit
    )
    val list: MutableList<Module> = mutableListOf()

    init {

        with(menu) {
            blackBoards.forEach {
                list.add(Module(it.name) {
                    onBlackBoardClickListener.onClickListener(it.link)
                })
            }

            if (forum != null) {
                list.add(Module("Forum") {
                    onForumClickListener.onClickListener(forum)
                })
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_board_menu, parent, false) as ViewGroup


        return ModuleViewHolder(view)
    }

    override fun getItemCount(): Int =
        list.size


    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bindToView(list[position])
    }

}

class ModuleViewHolder(view:ViewGroup):RecyclerView.ViewHolder(view){

    private val moduleName : TextView = view.findViewById(R.id.moduleName)
    private val layout : ConstraintLayout = view.findViewById(R.id.menuItemLayout)


    fun bindToView(smallModule: BoardMenuAdapter.Module){

        moduleName.text = smallModule.title

        layout.setOnClickListener {
            smallModule.onClickListener()
        }
    }
}

interface BlackBoardClickListener {
    fun onClickListener(blackBoardLink: GetSingleBlackBoardLink)
}
interface ForumClickListener {
    fun onClickListener(forumLink: GetSingleForumLink)
}
