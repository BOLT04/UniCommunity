package isel.pt.unicommunity.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface OnClickListener<T>{
    fun onClick( value : T )
}


abstract class AbstractViewHolder<T>(view : View) : RecyclerView.ViewHolder(view){
    abstract fun bindToView(value : T)
}

interface ViewHolderProvider<P>{
    fun getInstance(view : ViewGroup) : AbstractViewHolder<P>
}

abstract class AbstractAdapter<OBJ>(
    private val list: List<OBJ>,
    private val layout : Int,
    private val viewHolderProvider: ViewHolderProvider<OBJ>
): RecyclerView.Adapter<AbstractViewHolder<OBJ>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<OBJ> {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false) as ViewGroup

        return viewHolderProvider.getInstance(view)
    }

    override fun getItemCount(): Int =
        list.size


    override fun onBindViewHolder(holder: AbstractViewHolder<OBJ>, position: Int) {
        holder.bindToView(list[position])
    }
}