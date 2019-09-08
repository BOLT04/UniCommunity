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
    reports : List<ReportView>
): AbstractAdapter<ReportView>(
    reports,
    R.layout.item_report,
    object : ViewHolderProvider<ReportView>{
        override fun getInstance(view: ViewGroup)
            = ReportItemVH(view)
    }
)

class ReportItemVH(
    view : View
): AbstractViewHolder<ReportView>(view) {

    private val nReports : TextView = view.findViewById(R.id.number_reports)
    private val username : TextView = view.findViewById(R.id.username)

    private val layout : ConstraintLayout = view.findViewById(R.id.report_layout)

    override fun bindToView(value: ReportView) {
        nReports.text = value.nReports
        username.text = value.username
    }
}