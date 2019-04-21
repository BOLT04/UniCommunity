package pt.isel.g20.unicommunity.forumItem.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.repository.ForumItemRepository
import pt.isel.g20.unicommunity.repository.BoardRepository

@Service
class ForumItemService(
        val forumItemsRepo: ForumItemRepository,
        val boardsRepo: BoardRepository
) : IForumItemService {
    override fun getAllForumItems(boardId: Long): Iterable<ForumItem> = forumItemsRepo.findAll()

    override fun getForumItemById(boardId: Long, forumItemId: Long) =
            forumItemsRepo.findByIdOrNull(forumItemId) ?: throw NotFoundForumItemException()

    override fun createForumItem(
            boardId: Long,
            name: String,
            content: String
    ): ForumItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()

        val forumItem = ForumItem(boardId, name, content)

        return forumItemsRepo.save(forumItem)
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