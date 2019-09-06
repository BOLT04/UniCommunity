package pt.isel.g20.unicommunity.features.user.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.user.model.User
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.repository.UsersBoardsRepository

@Service
class UserService(
        val usersRepo: UserRepository,
        val boardsRepo: BoardRepository,
        val usersBoardsRepo: UsersBoardsRepository
) {

    fun getAllUsers(): Iterable<User> = usersRepo.findAll()

    fun getUserById(userId: Long) =
            usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

    fun getUserByName(name: String) =
            usersRepo.findByName(name) ?: throw NotFoundUserException()

    fun getBoardMembers(boardId: Long): Iterable<User>{
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        return usersBoardsRepo.findByBoardId(boardId).map { it.user }
    }

    fun createUser(sessionUserId:Long, name: String, email: String, password: String, role: String, githubId: String?): User {
        val sessionUser = usersRepo.findByIdOrNull(sessionUserId) ?: throw ForbiddenException()
        if(sessionUser.role != ADMIN) throw ForbiddenException()

        if (getAllUsers().map { it.email }.contains(email)) {
            throw InvalidUserEmailException()
        }

        if(role != TEACHER && role != STUDENT && role != ADMIN)
            throw InvalidUserRoleException()

        val hashedPassword = BCryptPasswordEncoder().encode(password)
        val user = User(name, email, hashedPassword, role, githubId)

        return usersRepo.save(user)
    }

    fun editUser(
            sessionUser: User,
            userId: Long,
            name: String,
            email: String,
            password: String,
            role: String,
            githubId: String?
    ): User {
        val user = getUserById(userId)
        if(user.id != sessionUser.id && sessionUser.role != ADMIN) throw ForbiddenException()

        if(user.role != role && sessionUser.role != ADMIN)
            throw ForbiddenException()

        if(email != user.email && (getAllUsers().map { it.email }.contains(email)))
            throw InvalidUserEmailException()

        user.email = email
        user.name = name
        user.pw = BCryptPasswordEncoder().encode(password)
        user.githubId = password

        return usersRepo.save(user)
    }

    fun deleteUser(sessionUser: User, userId: Long): User {
        val user = getUserById(userId)
        if(user.id != sessionUser.id && sessionUser.role != ADMIN) throw ForbiddenException()

        usersRepo.delete(user)
        return user
    }

}