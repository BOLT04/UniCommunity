package pt.isel.g20.unicommunity.blackboard.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.repository.BlackboardRepository

@Service
class BlackboardService(val blackboardRepo: BlackboardRepository) : IBlackboardService {
    override fun getAllBlackboards(boardId: Long): Iterable<Blackboard> = blackboardRepo.findAll()

    override fun getBlackboardById(boardId: Long, bbId: Long) = blackboardRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()

    override fun createBlackboard(boardId: Long, name: String, notificationLevel: String, description: String?): Blackboard {

        val blackboard =
                if(description != null)
                    Blackboard(boardId, name, description, notificationLevel)
                else
                    Blackboard(boardId, name, notificationLevel)

        return blackboardRepo.save(blackboard)
    }

    override fun editBlackboard(boardId: Long, bbId: Long, name: String?, notificationLevel: String? ,description: String?): Blackboard {
        val blackboard = getBlackboardById(boardId, bbId)

        if(name != null)
            blackboard.name = name

        if(notificationLevel != null)
            blackboard.notificationLevel = notificationLevel

        if(description != null)
            blackboard.description = description

        return blackboardRepo.save(blackboard)
    }

    override fun deleteBlackboard(boardId: Long, bbId: Long): Blackboard {
        val blackboard = getBlackboardById(boardId, bbId)
        blackboardRepo.delete(blackboard)
        return blackboard
    }

}