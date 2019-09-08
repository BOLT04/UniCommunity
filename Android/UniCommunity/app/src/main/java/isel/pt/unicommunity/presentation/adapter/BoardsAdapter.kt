package isel.pt.unicommunity.presentation.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.links.GetSingleBoardLink
import isel.pt.unicommunity.model.links.SubscribeLink
import isel.pt.unicommunity.model.links.UnsubscribeLink


class PartialBoardView(
    val self: GetSingleBoardLink,
    val name:String,
    val id : String,
    val description:String?,
    val subscribeLink : SubscribeLink?,
    val unsubscribeLink : UnsubscribeLink?
)

open class BoardsAdapter(
    allBoards: List<PartialBoardView>,
    private val onBoardClickListener: OnClickListener<PartialBoardView>,
    private val context: Context,
    private val subscribeListener: OnClickListener<SubscribeLink>,
    private val unsubscribeListener: OnClickListener<UnsubscribeLink>
): AbstractAdapter<PartialBoardView>(
    allBoards,
    R.layout.item_all_boards,
    object : ViewHolderProvider<PartialBoardView>{
        override fun getInstance(
            view: ViewGroup
        )= BoardsVH(view,onBoardClickListener, context, subscribeListener, unsubscribeListener)
    }
)


class BoardsVH(
    view: ViewGroup,
    private val onBoardClickListener: OnClickListener<PartialBoardView>,
    private val context : Context,
    private val subscribeListener: OnClickListener<SubscribeLink>,
    private val unsubscribeListener: OnClickListener<UnsubscribeLink>

) : AbstractViewHolder<PartialBoardView>(view) {

    private val name : TextView = view.findViewById(R.id.title_sb)
    private val desc : TextView = view.findViewById(R.id.desc_sb)
    private val subBtn : Button = view.findViewById(R.id.subBtn)
    private val layout : ConstraintLayout = view.findViewById(R.id.constraint_layout)
    
    override fun bindToView(value: PartialBoardView) {
        name.text = value.name
        value.description?.let{desc.text = it}

        subBtn.isVisible = true

        when {
            value.subscribeLink != null -> {
                subBtn.text = context.getString(R.string.subscribe)
                subBtn.setOnClickListener {
                    subscribeListener.onClick(value.subscribeLink)
                    subBtn.isVisible = false
                }
            }
            value.unsubscribeLink != null -> {
                subBtn.text = context.getString(R.string.unsubscribe)
                subBtn.setOnClickListener {
                    unsubscribeListener.onClick(value.unsubscribeLink)
                    subBtn.isVisible = false
                }
            }
            else -> subBtn.isVisible = false
        }


        layout.setOnClickListener{onBoardClickListener.onClick(value)}
    }
    
}






