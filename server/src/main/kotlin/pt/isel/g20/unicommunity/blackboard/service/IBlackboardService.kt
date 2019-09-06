package pt.isel.g20.unicommunity.blackboard.service

import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException

interface IBlackboardService {
    @Throws(NotFoundBoardException::class)
    fun getAllBlackboards(boardId: Long) : Iterable<Blackboard>

    @Throws(NotFoundBlackboardException::class, NotFoundBoardException::class)
    fun getBlackboardById(boardId: Long, bbId: Long): Blackboard

    @Throws(NotFoundBoardException::class)
    fun createBlackboard(boardId: Long, name: String, notificationLevel: String, description: String?): Blackboard

    @Throws(NotFoundBlackboardException::class, NotFoundBoardException::class)
    fun editBlackboard(boardId: Long, bbId: Long, name: String?, notificationLevel: String?, description: String?): Blackboard

    @Throws(NotFoundBlackboardException::class, NotFoundBoardException::class)
    fun deleteBlackboard(boardId: Long, bbId: Long): Blackboard
}