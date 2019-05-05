package pt.isel.g20.unicommunity.blackboardItem.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboardItem.exception.NotFoundBlackboardItemException
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.repository.BlackboardItemRepository
import pt.isel.g20.unicommunity.repository.BlackboardRepository
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException

@Service
class BlackboardItemService(
        val boardsRepo: BoardRepository,
        val blackboardsRepo: BlackboardRepository,
        val blackboardItemsRepo: BlackboardItemRepository,
        val usersRepo: UserRepository
) : IBlackboardItemService {
    override fun getAllBlackboardItems(boardId: Long, bbId: Long): Iterable<BlackboardItem> {
            boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
            return blackboardsRepo.findByIdOrNull(bbId)?.items ?: throw NotFoundBlackboardException()
    }

    override fun getBlackboardItemById(boardId: Long, bbId: Long, itemId: Long) =
            blackboardItemsRepo.findByIdOrNull(itemId) ?: throw NotFoundBlackboardItemException()

    override fun createBlackboardItem(
            boardId: Long,
            bbId: Long,
            userId: Long,
            name: String,
            content: String
    ): BlackboardItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        val blackboard = blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

        val blackboardItem = BlackboardItem(name, content, user, blackboard)
        val newBlackboardItem =  blackboardItemsRepo.save(blackboardItem)

        user.bbItems.add(newBlackboardItem)
        usersRepo.save(user)
        blackboard.items.add(newBlackboardItem)
        blackboardsRepo.save(blackboard)

        return newBlackboardItem
    }

    override fun editBlackboardItem(
            boardId: Long,
            bbId: Long,
            itemId: Long,
            name: String?,
            content: String?
    ): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)

        if(name != null)
            blackboardItem.name = name

        if(content != null)
            blackboardItem.content = content

        return blackboardItemsRepo.save(blackboardItem)
    }

    override fun deleteBlackboardItem(boardId: Long, bbId: Long, itemId: Long): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)
        blackboardItemsRepo.delete(blackboardItem)
        return blackboardItem
    }

}