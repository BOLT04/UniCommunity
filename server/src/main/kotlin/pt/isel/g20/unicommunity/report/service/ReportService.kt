package pt.isel.g20.unicommunity.report.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.report.model.Report
import pt.isel.g20.unicommunity.repository.CommentRepository
import pt.isel.g20.unicommunity.repository.ForumItemRepository
import pt.isel.g20.unicommunity.repository.ReportRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.user.model.User

@Service
class ReportService(
        val reportsRepo: ReportRepository,
        val usersRepo: UserRepository,
        val forumItemsRepo: ForumItemRepository,
        val commentsRepo: CommentRepository
) {
    fun getAllReports(user: User): Iterable<Report> {
        if(user.role != ADMIN) throw ForbiddenException()
        return reportsRepo.findAll()
    }

    fun getReportById(user: User, reportId: Long): Report {
        if(user.role != ADMIN) throw ForbiddenException()
        return reportsRepo.findByIdOrNull(reportId) ?: throw NotFoundReportException()
    }

    fun createReport(userId: Long, forumItemId: Long?, commentId: Long?): Report {
        if(forumItemId == null && commentId == null) throw InvalidReportTypeException()
        if(forumItemId != null && commentId != null) throw InvalidReportTypeException()
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

        if(commentId != null) {
            val comment = commentsRepo.findByIdOrNull(commentId) ?: throw NotFoundCommentException()
            var report = reportsRepo.findByUserAndComment(user, comment)
            if (report == null)
                report = Report(user, null, comment, 1)
            else
                report.numberOfReports++

            return reportsRepo.save(report)
        }
        else{
            val forumItem = forumItemsRepo.findByIdOrNull(forumItemId!!) ?: throw NotFoundForumItemException()
            var report = reportsRepo.findByUserAndForumItem(user, forumItem)
            if (report == null)
                report = Report(user, forumItem, null, 1)
            else
                report.numberOfReports++

            return reportsRepo.save(report)
        }
    }

    fun deleteReport(user: User, reportId: Long): Report {
        if(user.role != ADMIN) throw ForbiddenException()
        val report = reportsRepo.findByIdOrNull(reportId) ?: throw NotFoundReportException()

        reportsRepo.delete(report)
        return report
    }
}