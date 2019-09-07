package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.features.usersBlackboards.model.UsersBlackboards
import javax.transaction.Transactional

@Repository
@Transactional
interface UsersBlackboardsRepository : CrudRepository<UsersBlackboards, Long>{
    fun findByUserIdAndBlackboardId(userId: Long, blackboardId: Long): UsersBlackboards?
}