package isel.pt.unicommunity.model.links

import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.inputdto.ReportInputDto
import isel.pt.unicommunity.model.outputdto.ReportOutputDto

class GetSingleReportLink(href : String)
    : NavLink<ReportInputDto>(Rels.GET_SINGLE_REPORT, href, ReportInputDto::class.java)

class GetMultipleReportsLink(href : String)
    : NavLink<CollectionJson>(Rels.GET_SINGLE_REPORT, href, CollectionJson::class.java)

class CreateReportLink(href: String)
    : BodyNavLink<ReportOutputDto, ReportInputDto>(
        Rels.CREATE_REPORT,
        href,
        ReportOutputDto::class.java,
        ReportInputDto::class.java
    )