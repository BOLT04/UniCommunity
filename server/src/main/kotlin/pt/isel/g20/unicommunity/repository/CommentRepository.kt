package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.features.comment.model.Comment
import javax.transaction.Transactional

@Repository
@Transactional
interface CommentRepository : CrudRepository<Comment, Long>{
    fun findByForumItemIdOrderByCreatedAtDesc(forumItemId: Long): List<Comment>
    fun findByForumItemIdAndId(forumItemId: Long, commentId: Long): Comment?
}