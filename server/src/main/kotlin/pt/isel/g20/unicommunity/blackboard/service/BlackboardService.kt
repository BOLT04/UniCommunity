package pt.isel.g20.unicommunity.blackboard.service

import org.hibernate.Hibernate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.common.NotFoundBlackboardException
import pt.isel.g20.unicommunity.common.NotFoundBoardException
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.repository.BlackboardRepository
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.repository.UsersBlackboardsRepository
import pt.isel.g20.unicommunity.usersBlackboards.UsersBlackboards

@Service
class BlackboardService(
        val blackboardsRepo: BlackboardRepository,
        val boardsRepo: BoardRepository,
        val usersRepo: UserRepository,
        val usersBlackboardsRepository: UsersBlackboardsRepository
) : IBlackboardService {
    override fun getAllBlackboards(boardId: Long): Iterable<Blackboard> {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        return blackboardsRepo.findByBoardId(boardId).asIterable()
    }

    override fun getBlackboardById(boardId: Long, bbId: Long): Blackboard{
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        return blackboardsRepo.findByBoardIdAndId(boardId, bbId) ?: throw NotFoundBlackboardException()
    }

    override fun createBlackboard(
            userId: Long,
            boardId: Long,
            name: String,
            notificationLevel: String,
            description: String?
    ): Blackboard {
        val board = boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

        var blackboard = Blackboard(name, notificationLevel, description, board)
        blackboard = blackboardsRepo.save(blackboard)

        val userBlackboard = UsersBlackboards(user, blackboard, notificationLevel)
        usersBlackboardsRepository.save(userBlackboard)

        blackboard.usersSettings.add(userBlackboard)
        blackboard = blackboardsRepo.save(blackboard)

        user.blackboardsSettings.add(userBlackboard)
        usersRepo.save(user)

        val newBlackboard = blackboardsRepo.save(blackboard)

        board.blackBoards.add(newBlackboard)
        boardsRepo.save(board)

        return newBlackboard
    }

    override fun editBlackboard(
            boardId: Long,
            bbId: Long,
            name: String?,
            notificationLevel: String? ,
            description: String?
    ): Blackboard {
        val blackboard = getBlackboardById(boardId, bbId)

        if(name != null)
            blackboard.name = name

        if(notificationLevel != null)
            blackboard.notificationLevel = notificationLevel

        if(description != null)
            blackboard.description = description

        return blackboardsRepo.save(blackboard)
    }

    override fun deleteBlackboard(boardId: Long, bbId: Long): Blackboard {
        val blackboard = getBlackboardById(boardId, bbId)

        Hibernate.initialize(blackboard.items)

        blackboardsRepo.delete(blackboard)
        return blackboard
    }

}