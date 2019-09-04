package pt.isel.g20.unicommunity.report

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.REPORTS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_REPORT_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.report.model.*
import pt.isel.g20.unicommunity.report.service.ReportService
import pt.isel.g20.unicommunity.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class ReportController(private val service: ReportService) {

    @GetMapping(path = [REPORTS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    @AuthorizationRequired
    fun getAllReports(
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    CollectionObject(
                            MultipleReportsResponse(
                                    service.getAllReports(user).map(Report::toItemRepr)
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [SINGLE_REPORT_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getBoardById(
            @PathVariable reportId: Long,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(SingleReportResponse(service.getReportById(user, reportId)))

    @AuthorizationRequired
    @PostMapping(path = [REPORTS_ROUTE], produces = [APPLICATION_HAL_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBoard(
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
                cacheCreatedResponse(responseBody, newResourceHref)
            }
}