package pt.isel.g20.unicommunity.BlackboardItem.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboardItem.exception.NotFoundBlackboardItemException
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.blackboardItem.service.IBlackboardItemService
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.repository.BlackboardItemRepository
import pt.isel.g20.unicommunity.repository.BlackboardRepository
import pt.isel.g20.unicommunity.repository.BoardRepository

@Service
class BlackboardItemService(
        val boardsRepo: BoardRepository,
        val blackboardsRepo: BlackboardRepository,
        val blackboardItemsRepo: BlackboardItemRepository
) : IBlackboardItemService {
    override fun getAllBlackboardItems(boardId: Long, bbId: Long): Iterable<BlackboardItem> =
            blackboardItemsRepo.findAll()

    override fun getBlackboardItemById(boardId: Long, bbId: Long, itemId: Long) =
            blackboardItemsRepo.findByIdOrNull(itemId) ?: throw NotFoundBlackboardItemException()

    override fun createBlackboardItem(
            boardId: Long,
            bbId: Long,
            name: String,
            content: String
    ): BlackboardItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()

        val blackboard = blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()

        val blackboardItem = BlackboardItem(name, content)

        blackboardItem.blackboard = blackboard
        return blackboardItemsRepo.save(blackboardItem)
    }

    override fun editBlackboardItem(boardId: Long, bbId: Long, itemId: Long, name: String?, content: String?): BlackboardItem {
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