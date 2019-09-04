package pt.isel.g20.unicommunity.user.service

import org.hibernate.Hibernate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.repository.UsersBoardsRepository
import pt.isel.g20.unicommunity.user.model.User

@Service
class UserService(
        val usersRepo: UserRepository,
        val boardsRepo: BoardRepository,
        val usersBoardsRepo: UsersBoardsRepository
        //val passwordEncoder: PasswordEncoder
) : IUserService {

    override fun getAllUsers(): Iterable<User> = usersRepo.findAll()

    override fun getUserById(userId: Long) =
            usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

    override fun getUserByName(name: String) =
            usersRepo.findByName(name) ?: throw NotFoundUserException()

    override fun getBoardMembers(boardId: Long): Iterable<User>{
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        return usersBoardsRepo.findByBoardId(boardId).map { it.user }
    }

    override fun createUser(sessionUserId:Long, name: String, email: String, password: String, role: String, githubId: String?): User {
        val sessionUser = usersRepo.findByIdOrNull(sessionUserId) ?: throw UnauthorizedException()
        if(sessionUser.role != ADMIN) throw UnauthorizedException()

        if (getAllUsers().map { it.email }.contains(email)) {
            throw InvalidUserEmailException()
        }

        if(role != TEACHER && role != STUDENT && role != ADMIN)
            throw InvalidUserRoleException()

        val hashedPassword = BCryptPasswordEncoder().encode(password)
        val user = User(name, email, hashedPassword, role, githubId)

        return usersRepo.save(user)
    }

    override fun editUser(
            sessionUser: User,
            userId: Long,
            name: String,
            email: String,
            password: String,
            role: String,
            githubId: String?
    ): User {
        val user = getUserById(userId)
        if(user.id != sessionUser.id || sessionUser.role != ADMIN) throw UnauthorizedException()

        if(sessionUser.role != ADMIN && user.role != role)
            throw UnauthorizedException()

        if(email != user.email && (getAllUsers().map { it.email }.contains(email)))
            throw InvalidUserEmailException()

        user.email = email
        user.name = name
        user.pw = BCryptPasswordEncoder().encode(password)
        user.githubId = password

        return usersRepo.save(user)
    }

    override fun deleteUser(sessionUser: User, userId: Long): User {
        val user = getUserById(userId)
        if(user.id != sessionUser.id || sessionUser.role != ADMIN) throw UnauthorizedException()

        Hibernate.initialize(user.bbItems)
        Hibernate.initialize(user.usersBoards)
        Hibernate.initialize(user.comments)
        Hibernate.initialize(user.forumItems)
        Hibernate.initialize(user.blackboardsSettings)

        usersRepo.delete(user)
        return user
    }

}