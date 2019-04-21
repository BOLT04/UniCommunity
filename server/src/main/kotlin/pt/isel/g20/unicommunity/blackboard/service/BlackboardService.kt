package pt.isel.g20.unicommunity.blackboard.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.repository.BlackboardRepository
import pt.isel.g20.unicommunity.repository.BoardRepository

@Service
class BlackboardService(
        val blackboardsRepo: BlackboardRepository,
        val boardsRepo: BoardRepository
) : IBlackboardService {
    override fun getAllBlackboards(boardId: Long): Iterable<Blackboard> = blackboardsRepo.findAll()

    override fun getBlackboardById(boardId: Long, bbId: Long) =
            blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()

    override fun createBlackboard(
            boardId: Long,
            name: String,
            notificationLevel: String,
            description: String?
    ): Blackboard {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()

        val blackboard =
                if(description != null)
                    Blackboard(boardId, name, description, notificationLevel)
                else
                    Blackboard(boardId, name, notificationLevel)

        return blackboardsRepo.save(blackboard)
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
        blackboardsRepo.delete(blackboard)
        return blackboard
    }

}