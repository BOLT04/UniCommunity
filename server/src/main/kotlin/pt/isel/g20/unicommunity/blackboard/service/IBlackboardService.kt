package pt.isel.g20.unicommunity.blackboard.service

import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.common.NotFoundBlackboardException
import pt.isel.g20.unicommunity.common.NotFoundBoardException
import pt.isel.g20.unicommunity.user.model.User

interface IBlackboardService {
    @Throws(NotFoundBoardException::class)
    fun getAllBlackboards(boardId: Long) : Iterable<Blackboard>

    @Throws(NotFoundBlackboardException::class, NotFoundBoardException::class)
    fun getBlackboardById(boardId: Long, bbId: Long): Blackboard

    @Throws(NotFoundBoardException::class)
    fun createBlackboard(userId: Long, boardId: Long, name: String, notificationLevel: String, description: String?): Blackboard

    @Throws(NotFoundBlackboardException::class, NotFoundBoardException::class)
    fun editBlackboard(user: User, boardId: Long, bbId: Long, name: String?, notificationLevel: String?, description: String?): Blackboard

    @Throws(NotFoundBlackboardException::class, NotFoundBoardException::class)
    fun deleteBlackboard(user: User, boardId: Long, bbId: Long): Blackboard
}