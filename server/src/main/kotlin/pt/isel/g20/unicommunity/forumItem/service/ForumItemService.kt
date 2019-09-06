package pt.isel.g20.unicommunity.forumItem.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.repository.ForumItemRepository
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.ForumRepository

@Service
class ForumItemService(
        val forumItemsRepo: ForumItemRepository,
        val forumsRepo: ForumRepository,
        val boardsRepo: BoardRepository
) : IForumItemService {
    override fun getAllForumItems(boardId: Long): Iterable<ForumItem> =
            forumsRepo.findByIdOrNull(boardId)?.items ?: throw NotFoundForumException()

    override fun getForumItemById(boardId: Long, forumItemId: Long) =
            forumItemsRepo.findByIdOrNull(forumItemId) ?: throw NotFoundForumItemException()

    override fun createForumItem(
            boardId: Long,
            name: String,
            content: String
    ): ForumItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        val forum = forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()

        val forumItem = ForumItem(name, content, "Luis Vaz")

        forumItem.forum = forum
        forum.items.add(forumItem)
        val newForumItem = forumItemsRepo.save(forumItem)
        forumsRepo.save(forum)

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