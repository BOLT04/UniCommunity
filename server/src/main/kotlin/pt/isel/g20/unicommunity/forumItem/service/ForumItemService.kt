package pt.isel.g20.unicommunity.forumItem.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.NotFoundBoardException
import pt.isel.g20.unicommunity.common.NotFoundForumException
import pt.isel.g20.unicommunity.common.NotFoundForumItemException
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.repository.ForumItemRepository
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.ForumRepository
import pt.isel.g20.unicommunity.repository.UserRepository

@Service
class ForumItemService(
        val forumItemsRepo: ForumItemRepository,
        val forumsRepo: ForumRepository,
        val boardsRepo: BoardRepository,
        val usersRepo: UserRepository
) : IForumItemService {
    override fun getAllForumItems(boardId: Long): Iterable<ForumItem>{
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        val forum = forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        return forum.items
    }

    override fun getForumItemById(boardId: Long, forumItemId: Long): ForumItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        val forum = forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        return forum.items.find { it.id == forumItemId } ?: throw NotFoundForumItemException()
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
            boardId: Long,
            forumItemId: Long,
            name: String?,
            content: String?
    ): ForumItem {
        val forumItem = getForumItemById(boardId, forumItemId)

        if(name != null)
            forumItem.name = name

        if(content != null)
            forumItem.content = content

        return forumItemsRepo.save(forumItem)
    }

    override fun deleteForumItem(boardId: Long, forumItemId: Long): ForumItem {
        val forumItem = getForumItemById(boardId, forumItemId)
        forumItemsRepo.delete(forumItem)
        return forumItem
    }

}