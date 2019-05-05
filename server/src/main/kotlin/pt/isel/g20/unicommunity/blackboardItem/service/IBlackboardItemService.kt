package pt.isel.g20.unicommunity.blackboardItem.service

import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboardItem.exception.NotFoundBlackboardItemException
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException

interface IBlackboardItemService {
    @Throws(NotFoundBoardException::class, NotFoundBlackboardException::class)
    fun getAllBlackboardItems(boardId: Long, bbId: Long) : Iterable<BlackboardItem>

    @Throws(NotFoundBlackboardException::class, NotFoundBoardException::class, NotFoundBlackboardItemException::class)
    fun getBlackboardItemById(boardId: Long, bbId: Long, itemId: Long): BlackboardItem

    @Throws(NotFoundBoardException::class, NotFoundBlackboardException::class, NotFoundUserException::class)
    fun createBlackboardItem(boardId: Long, bbId: Long, userId: Long, name: String, content: String): BlackboardItem

    @Throws(NotFoundBlackboardException::class, NotFoundBoardException::class, NotFoundBlackboardItemException::class)
    fun editBlackboardItem(boardId: Long, bbId: Long, itemId: Long, name: String?, content: String?): BlackboardItem

    @Throws(NotFoundBlackboardException::class, NotFoundBoardException::class, NotFoundBlackboardItemException::class)
    fun deleteBlackboardItem(boardId: Long, bbId: Long, itemId: Long): BlackboardItem
}