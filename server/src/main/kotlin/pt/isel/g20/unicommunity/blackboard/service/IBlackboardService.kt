package pt.isel.g20.unicommunity.blackboard.service

import pt.isel.g20.unicommunity.blackboard.model.Blackboard

interface IBlackboardService {
    fun getAllBlackboards() : Collection<Blackboard>
    fun createBlackboard(blackboard: Blackboard): Blackboard
    fun getBlackboardById(blackboardId: String): Blackboard?
}