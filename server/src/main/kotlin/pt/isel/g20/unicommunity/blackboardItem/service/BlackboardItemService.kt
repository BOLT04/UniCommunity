package pt.isel.g20.unicommunity.blackboardItem.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.repository.BlackboardItemRepository
import pt.isel.g20.unicommunity.repository.BlackboardRepository
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.user.model.User

@Service
class BlackboardItemService(
        val boardsRepo: BoardRepository,
        val blackboardsRepo: BlackboardRepository,
        val blackboardItemsRepo: BlackboardItemRepository,
        val usersRepo: UserRepository
) {
    fun getAllBlackboardItems(boardId: Long, bbId: Long): Iterable<BlackboardItem> {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        return blackboardItemsRepo.findByBlackboardIdOrderByCreatedAtDesc(bbId).asIterable()
    }

    fun getBlackboardItemById(boardId: Long, bbId: Long, itemId: Long) : BlackboardItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        return blackboardItemsRepo.findByBlackboardIdAndId(bbId, itemId) ?: throw NotFoundBlackboardItemException()
    }

    fun createBlackboardItem(
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

    fun editBlackboardItem(
            user: User,
            boardId: Long,
            bbId: Long,
            itemId: Long,
            name: String?,
            content: String?
    ): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)
        if(user.id != blackboardItem.blackboard.board.creator.id && user.role != ADMIN) throw ForbiddenException()

        if(name != null)
            blackboardItem.name = name

        if(content != null)
            blackboardItem.content = content

        return blackboardItemsRepo.save(blackboardItem)
    }

    fun deleteBlackboardItem(user: User, boardId: Long, bbId: Long, itemId: Long): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)
        if(user.id != blackboardItem.blackboard.board.creator.id && user.role != ADMIN) throw ForbiddenException()

        blackboardItemsRepo.delete(blackboardItem)
        return blackboardItem
    }

}