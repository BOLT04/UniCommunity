package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.toReportCollection
import isel.pt.unicommunity.model.links.GetMultipleReportsLink
import isel.pt.unicommunity.presentation.adapter.ReportView
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkGetRequest

class ReportViewModel(
    private val app: UniCommunityApp,
    private val multipleReportsLink: GetMultipleReportsLink
): ViewModel() {

    val reportsLd = ErrorHandlingMLD<List<ReportView>, String>()

    fun fetchReports(){

        val req = BasicAuthNavLinkGetRequest(
            multipleReportsLink,
            Response.Listener { collectionJson ->
                reportsLd.success(
                    collectionJson.toReportCollection().reports.map {
                        ReportView(
                            it.userName,
                            it.numberOfReports,
                            it.self
                        )
                    }
                )
            },
            Response.ErrorListener { reportsLd.error(it.message ?: it.localizedMessage) },
            app.email,
            app.password
        )

        app.queue.add(req)


    }


}
