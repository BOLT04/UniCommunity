package isel.pt.unicommunity.model.inputdto

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.collectionjson.DataExtractor
import isel.pt.unicommunity.model.links.GetMultipleReportsLink
import isel.pt.unicommunity.model.links.GetSingleReportLink

class ReportInputDto(
    _links : ReportLinkStruct,
    val id : String,
    val numberOfReports : String
) {
    class ReportLinkStruct(
        self : GetSingleReportLink,
        @JsonProperty(Rels.GET_MULTIPLE_REPORTS) getMultipleReportsLink : GetMultipleReportsLink?
    )
}

