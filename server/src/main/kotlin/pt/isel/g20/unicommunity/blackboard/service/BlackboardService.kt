package pt.isel.g20.unicommunity.blackboard.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.model.Blackboard

@Service
class BlackboardService : IBlackboardService {
    val blackboards = hashMapOf<String, Blackboard>()

    private val logger = LoggerFactory.getLogger(BlackboardService::class.java)

    @Synchronized
    override fun getAllBlackboards() = blackboards.values

    @Synchronized
    override fun createBlackboard(blackboard: Blackboard): Blackboard {
        blackboards[blackboard.name] = blackboard
        return blackboard
    }

    override fun getBlackboardById(blackboardId: String) = blackboards[blackboardId]
}