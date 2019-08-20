package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.comment.model.Comment
import javax.transaction.Transactional

@Repository
@Transactional
interface CommentRepository : CrudRepository<Comment, Long>{
    fun findByAnonymousCommentTrue(): List<Comment>
    fun findByAnonymousCommentFalse(): List<Comment>
    fun findByForumItemIdOrderByCreatedAtDesc(forumItemId: Long): List<Comment>
}