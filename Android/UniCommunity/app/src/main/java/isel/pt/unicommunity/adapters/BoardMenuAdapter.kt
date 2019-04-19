package isel.pt.unicommunity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.SmallModule
import isel.pt.unicommunity.viewmodel.BoardViewModel
import kotlinx.android.synthetic.main.board_menu_layout.view.*


class BoardMenuAdapter(
    val viewModel: BoardViewModel,
    val smallModuleClickListener : SmallModuleClickListener
): RecyclerView.Adapter<ModuleViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.board_menu_layout, parent, false) as ViewGroup

        return ModuleViewHolder(view, smallModuleClickListener)
    }

    override fun getItemCount(): Int =
        viewModel.liveData.value?.size ?: 0


    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bindToView(viewModel.liveData.value?.get(position))
    }

}

class ModuleViewHolder(view:ViewGroup, val smallModuleClickListener: SmallModuleClickListener):RecyclerView.ViewHolder(view){

    val moduleName : TextView = view.findViewById(R.id.moduleName)
    val layout : ConstraintLayout = view.findViewById(R.id.menuItemLayout)


    fun bindToView(smallModule: SmallModule?){

        moduleName.text = smallModule?.name ?: "no title"

        layout.setOnClickListener {
            smallModuleClickListener.onClickListener(smallModule)
        }
    }
}

interface SmallModuleClickListener {
    fun onClickListener(smallModule: SmallModule?)
}
