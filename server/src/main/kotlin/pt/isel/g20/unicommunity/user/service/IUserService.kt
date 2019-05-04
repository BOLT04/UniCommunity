package pt.isel.g20.unicommunity.user.service

import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.user.exception.InvalidUserEmailException
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException
import pt.isel.g20.unicommunity.user.model.User

interface IUserService {
    fun getAllUsers() : Iterable<User>

    @Throws(NotFoundUserException::class)
    fun getUserById(userId: Long): User

    @Throws(InvalidUserEmailException::class)
    fun createUser(name: String, email: String, password:String, githubId:String?) : User

    @Throws(NotFoundUserException::class, InvalidUserEmailException::class)
    fun editUser(userId: Long, name: String?, email: String?, password:String?, githubId:String?): User

    @Throws(NotFoundUserException::class)
    fun deleteUser(userId: Long): User
}