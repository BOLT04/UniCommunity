package isel.pt.unicommunity.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import isel.pt.unicommunity.R
import isel.pt.unicommunity.model.links.GetSingleReportLink

class ReportView(
    val username : String,
    val nReports : String,
    val self : GetSingleReportLink
)

class ReportAdapter(
    reports : List<ReportView>,
    val onClickListener: OnClickListener<ReportView>
): AbstractAdapter<ReportView>(
    reports,
    R.layout.item_report,
    object : ViewHolderProvider<ReportView>{
        override fun getInstance(view: ViewGroup)
            = ReportItemVH(view, onClickListener)
    }
)

class ReportItemVH(
    view : View,
    private val onClickListener: OnClickListener<ReportView>
): AbstractViewHolder<ReportView>(view) {

    val nReports = view.findViewById<TextView>(R.id.number_reports)
    val username = view.findViewById<TextView>(R.id.username)

    val layout = view.findViewById<ConstraintLayout>(R.id.report_layout)

    override fun bindToView(value: ReportView) {
        nReports.text = value.nReports
        username.text = value.username

        layout.setOnClickListener { onClickListener.onClick(value) }
    }
}