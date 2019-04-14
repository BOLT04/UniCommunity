package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import pt.isel.g20.unicommunity.blackboard.model.Blackboard

interface BlackboardRepository : CrudRepository<Blackboard, Long>