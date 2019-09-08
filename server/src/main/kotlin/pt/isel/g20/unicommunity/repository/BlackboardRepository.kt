package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.features.blackboard.model.Blackboard
import javax.transaction.Transactional

@Repository
@Transactional
interface BlackboardRepository : CrudRepository<Blackboard, Long>{
    fun findByBoardIdAndId(boardId: Long, blackboardId: Long): Blackboard?
    fun findByBoardId(boardId: Long): List<Blackboard>
}