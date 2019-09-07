package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.features.forumItem.model.ForumItem
import javax.transaction.Transactional

@Repository
@Transactional
interface ForumItemRepository : CrudRepository<ForumItem, Long>{
    fun findByAnonymousPostTrue(): List<ForumItem>?
    fun findByAnonymousPostFalse(): List<ForumItem>?
    fun findByForumIdOrderByCreatedAtDesc(forumId: Long): List<ForumItem>
    fun findByForumIdAndId(forumId: Long, forumItemId: Long): ForumItem?
}