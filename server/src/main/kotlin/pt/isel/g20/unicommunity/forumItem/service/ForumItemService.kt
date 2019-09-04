package pt.isel.g20.unicommunity.forumItem.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.ForumItemRepository
import pt.isel.g20.unicommunity.repository.ForumRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.user.model.User

@Service
class ForumItemService(
        val forumItemsRepo: ForumItemRepository,
        val forumsRepo: ForumRepository,
        val boardsRepo: BoardRepository,
        val usersRepo: UserRepository
) : IForumItemService {
    override fun getAllForumItems(boardId: Long): Iterable<ForumItem>{
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        return forumItemsRepo.findByForumIdOrderByCreatedAtDesc(boardId).asIterable()
    }

    override fun getForumItemById(boardId: Long, forumItemId: Long): ForumItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        return forumItemsRepo.findByForumIdAndId(boardId, forumItemId) ?: throw NotFoundForumItemException()
    }

    override fun createForumItem(
            boardId: Long,
            authorId: Long,
            name: String,
            content: String,
            anonymousPost: Boolean
    ): ForumItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        val forum = forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        val user = usersRepo.findByIdOrNull(authorId) ?: throw NotFoundUserException()

        val forumItem = ForumItem(forum, user, name, content, anonymousPost)
        val newForumItem = forumItemsRepo.save(forumItem)

        forum.items.add(newForumItem)
        user.forumItems.add(newForumItem)
        forumsRepo.save(forum)
        usersRepo.save(user)

        return newForumItem
    }

    override fun editForumItem(
            user: User,
            boardId: Long,
            forumItemId: Long,
            name: String?,
            content: String?
    ): ForumItem {
        val forumItem = getForumItemById(boardId, forumItemId)
        if(user.id != forumItem.author.id && user.role != ADMIN) throw UnauthorizedException()

        if(name != null)
            forumItem.name = name

        if(content != null)
            forumItem.content = content

        return forumItemsRepo.save(forumItem)
    }

    override fun deleteForumItem(user: User, boardId: Long, forumItemId: Long): ForumItem {
        val forumItem = getForumItemById(boardId, forumItemId)
        if(user.id != forumItem.author.id && user.role != ADMIN) throw UnauthorizedException()

        forumItemsRepo.delete(forumItem)
        return forumItem
    }

}