package pt.isel.g20.unicommunity.blackboard.service

import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.board.model.Board

interface IBlackboardService {
    fun getAllBlackboards() : Collection<Blackboard>
    fun createBlackboard(blackboard: Blackboard): Blackboard
    fun getBlackboardById(blackboardId: String): Blackboard?
}