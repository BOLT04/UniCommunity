package pt.isel.g20.unicommunity.features.blackboard.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.features.user.model.User
import pt.isel.g20.unicommunity.features.usersBlackboards.model.UsersBlackboards
import pt.isel.g20.unicommunity.repository.BlackboardRepository
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.repository.UsersBlackboardsRepository

@Service
class BlackboardService(
        val blackboardsRepo: BlackboardRepository,
        val boardsRepo: BoardRepository,
        val usersRepo: UserRepository,
        val usersBlackboardsRepo: UsersBlackboardsRepository
) {
    fun getAllBlackboards(boardId: Long): Iterable<Blackboard> {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        return blackboardsRepo.findByBoardId(boardId).asIterable()
    }

    fun getBlackboardById(boardId: Long, bbId: Long): Blackboard{
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        return blackboardsRepo.findByBoardIdAndId(boardId, bbId) ?: throw NotFoundBlackboardException()
    }

    fun createBlackboard(
            userId: Long,
            boardId: Long,
            name: String,
            notificationLevel: String,
            description: String? = null
    ): Blackboard {
        val board = boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()
        if(user.id != board.creator.id && user.role != ADMIN) throw ForbiddenException()

        if(notificationLevel != NONE && notificationLevel != PRIORITY && notificationLevel != ALL)
            throw InvalidNotificationLevelException()

        var blackboard = Blackboard(name, notificationLevel, description, board)
        blackboard = blackboardsRepo.save(blackboard)

        board.getMembers().forEach {
            val userBlackboard = UsersBlackboards(it, blackboard, blackboard.notificationLevel)
            usersBlackboardsRepo.save(userBlackboard)

            user.blackboardsSettings.add(userBlackboard)
            usersRepo.save(it)

            blackboard.usersSettings.add(userBlackboard)
            blackboardsRepo.save(blackboard)
        }

        board.blackBoards.add(blackboard)
        boardsRepo.save(board)

        return blackboard
    }

    fun editBlackboard(
            user: User,
            boardId: Long,
            bbId: Long,
            name: String,
            notificationLevel: String,
            description: String?
    ): Blackboard {
        val blackboard = getBlackboardById(boardId, bbId)
        if(user.id != blackboard.board.creator.id && user.role != ADMIN) throw ForbiddenException()

        if(notificationLevel != NONE && notificationLevel != PRIORITY && notificationLevel != ALL)
            throw InvalidNotificationLevelException()

        blackboard.name = name
        blackboard.notificationLevel = notificationLevel

        if(description != null)
            blackboard.description = description

        return blackboardsRepo.save(blackboard)
    }

    fun deleteBlackboard(user: User, boardId: Long, bbId: Long): Blackboard {
        val blackboard = getBlackboardById(boardId, bbId)
        if(user.id != blackboard.board.creator.id && user.role != ADMIN) throw ForbiddenException()

        blackboardsRepo.delete(blackboard)
        return blackboard
    }

}