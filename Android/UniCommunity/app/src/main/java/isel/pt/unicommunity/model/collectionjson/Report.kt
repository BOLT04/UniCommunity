package isel.pt.unicommunity.model.collectionjson

import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.links.GetMultipleReportsLink
import isel.pt.unicommunity.model.links.GetSingleReportLink


fun CollectionJson.toReportCollection(): ReportCollection{

    with(this.collection){
        return ReportCollection(
            GetMultipleReportsLink(href),
            items.map {
                with(DataExtractor(it.data, it.links)) {
                    ReportCollection.PartialReports(
                        GetSingleReportLink(it.href),
                        getValue("id"),
                        getValue("userId"),
                        getValue("numberOfReports"),
                        getOptionalValue("forumItemId"),
                        getOptionalValue("commentId")
                    )
                }
            }
        )
    }



}

class ReportCollection(
    val self : GetMultipleReportsLink,
    val reports : List<PartialReports>
) {
    class PartialReports(
        val self: GetSingleReportLink,

        val id: String,
        val userId: String,
        val numberOfReports: String,

        val forumItemId : String?,
        val commentId : String?
    )
}