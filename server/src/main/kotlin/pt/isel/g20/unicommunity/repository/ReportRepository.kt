package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.features.comment.model.Comment
import pt.isel.g20.unicommunity.features.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.features.report.model.Report
import pt.isel.g20.unicommunity.features.user.model.User
import javax.transaction.Transactional

@Repository
@Transactional
interface ReportRepository : CrudRepository<Report, Long> {
    fun findByUser(user: User): List<Report>
    fun findByUserAndComment(user: User, comment: Comment) : Report?
    fun findByUserAndForumItem(user: User, forumItem: ForumItem) : Report?
}
