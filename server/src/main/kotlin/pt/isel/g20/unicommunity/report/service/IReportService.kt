package pt.isel.g20.unicommunity.report.service

import pt.isel.g20.unicommunity.report.model.Report
import pt.isel.g20.unicommunity.user.model.User

interface IReportService {
    fun getAllReports(user: User): Iterable<Report>
    fun getReportById(user: User, reportId: Long): Report
    fun createReport(userId: Long, forumItemId: Long?, commentId: Long?): Report
}