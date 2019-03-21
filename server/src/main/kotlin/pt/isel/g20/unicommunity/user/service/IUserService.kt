package pt.isel.g20.unicommunity.user.service

import pt.isel.g20.unicommunity.user.model.User

interface IUserService {
    fun getAllUsers() : Collection<User>
    fun createUser(user: User)
    fun getUserById(userId: String): User?
}