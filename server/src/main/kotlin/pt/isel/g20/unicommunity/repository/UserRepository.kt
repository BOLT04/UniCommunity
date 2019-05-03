package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.user.model.User
import javax.transaction.Transactional

@Repository
@Transactional
interface UserRepository : CrudRepository<User, Long> {
    fun findByName(username: String?): User?
}
