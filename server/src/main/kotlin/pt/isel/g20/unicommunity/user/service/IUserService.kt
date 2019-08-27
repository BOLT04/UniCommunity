package pt.isel.g20.unicommunity.user.service

import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.user.model.User

interface IUserService {
    fun getAllUsers() : Iterable<User>

    @Throws(NotFoundUserException::class)
    fun getUserById(userId: Long): User

    @Throws(NotFoundUserException::class)
    fun getUserByName(name: String): User

    @Throws(
            InvalidUserEmailException::class,
            UnauthorizedException::class,
            InvalidUserRoleException::class
    )
    fun createUser(
            sessionUserId: Long,
            name: String,
            email: String,
            password:String,
            role: String,
            githubId:String?
    ): User

    @Throws(NotFoundUserException::class, InvalidUserEmailException::class)
    fun editUser(userId: Long, name: String?, email: String?, password:String?, githubId:String?): User

    @Throws(NotFoundUserException::class)
    fun deleteUser(userId: Long): User

    @Throws(NotFoundBoardException::class)
    fun getBoardMembers(boardId: Long) : Iterable<User>
}