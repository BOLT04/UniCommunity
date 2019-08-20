package pt.isel.g20.unicommunity.blackboardItem.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.common.NotFoundBlackboardException
import pt.isel.g20.unicommunity.common.NotFoundBlackboardItemException
import pt.isel.g20.unicommunity.common.NotFoundBoardException
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.repository.BlackboardItemRepository
import pt.isel.g20.unicommunity.repository.BlackboardRepository
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository

@Service
class BlackboardItemService(
        val boardsRepo: BoardRepository,
        val blackboardsRepo: BlackboardRepository,
        val blackboardItemsRepo: BlackboardItemRepository,
        val usersRepo: UserRepository
) : IBlackboardItemService {
    override fun getAllBlackboardItems(boardId: Long, bbId: Long): Iterable<BlackboardItem> {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        return blackboardItemsRepo.findByBlackboardIdOrderByCreatedAtDesc(bbId).asIterable()
    }

    override fun getBlackboardItemById(boardId: Long, bbId: Long, itemId: Long) : BlackboardItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        return blackboardItemsRepo.findByBlackboardIdAndId(bbId, itemId) ?: throw NotFoundBlackboardItemException()
    }

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