package pt.isel.g20.unicommunity.features.report

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.REPORTS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_REPORT_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.features.report.model.*
import pt.isel.g20.unicommunity.features.report.service.ReportService
import pt.isel.g20.unicommunity.features.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class ReportController(private val service: ReportService) {

    @AuthorizationRequired
    @GetMapping(path = [REPORTS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllReports(
            @SessionAttribute("user") user: User
    ) =
            okResponse(
                    CollectionObject(
                            MultipleReportsResponse(
                                    service.getAllReports(user).map(Report::toItemRepr)
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [SINGLE_REPORT_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getReportById(
            @PathVariable reportId: Long,
            @SessionAttribute("user") user: User
    ) =
            okResponse(SingleReportResponse(service.getReportById(user, reportId)))

    @AuthorizationRequired
    @PostMapping(path = [REPORTS_ROUTE], produces = [APPLICATION_HAL_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createReport(
            @RequestBody reportDto: ReportDto,
            @SessionAttribute("user")user: User
    ) =
            service.createReport(
                    reportDto.userId,
                    reportDto.forumItemId,
                    reportDto.commentId
            ).let {
                val responseBody = SingleReportResponse(it)
                val newResourceHref = Uri.forSingleBoardUri(it.id)
                createdResponse(responseBody, newResourceHref)
            }

    @AuthorizationRequired
    @DeleteMapping(path = [SINGLE_REPORT_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun deleteReport(
            @PathVariable reportId: Long,
            @SessionAttribute("user") user: User
    ) =
            okResponse(SingleReportResponse(service.deleteReport(user, reportId)))
}