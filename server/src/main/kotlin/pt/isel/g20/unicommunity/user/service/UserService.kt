package pt.isel.g20.unicommunity.user.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.user.model.User

@Service
class UserService : IUserService {
    val users = hashMapOf<String, User>()

    private val logger = LoggerFactory.getLogger(UserService::class.java)

    @Synchronized
    override fun getAllUsers() = users.values

    @Synchronized
    override fun createUser(user: User) {
        users[user.id] = user
    }

    override fun getUserById(userId: String) = users[userId]
}