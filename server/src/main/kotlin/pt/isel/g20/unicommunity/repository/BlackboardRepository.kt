package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import javax.transaction.Transactional

@Repository
@Transactional
interface BlackboardRepository : CrudRepository<Blackboard, Long>