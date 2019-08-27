package pt.isel.g20.unicommunity.user.service

import org.hibernate.Hibernate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.user.model.User

@Service
class UserService(val usersRepo: UserRepository, val boardsRepo: BoardRepository) : IUserService {

    override fun getAllUsers(): Iterable<User> = usersRepo.findAll()

    override fun getUserById(userId: Long) =
            usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()
    override fun getUserByName(name: String) =
            usersRepo.findByName(name) ?: throw NotFoundUserException()

    override fun createUser(sessionUserId:Long, name: String, email: String, password: String, role: String, githubId: String?): User {
        val sessionUser = usersRepo.findByIdOrNull(sessionUserId) ?: throw UnauthorizedException()
        if(sessionUser.role != "admin") throw UnauthorizedException()

        if (getAllUsers().map { it.email }.contains(email)) {
            throw InvalidUserEmailException()
        }

        if(role != "teacher" && role !="student" && role != "admin")
            throw InvalidUserRoleException()

        val user = User(name, email, password, role, githubId)

        return usersRepo.save(user)
    }

    override fun editUser(userId: Long, name: String?, email: String?, password: String?, githubId: String?): User {
        val user = getUserById(userId)

        if(email != null) {
            if(
                    email != user.email
                    &&
                    (getAllUsers().map { it.email }.contains(email))
            )
                throw InvalidUserEmailException()

            user.email = email
        }
        if(name != null)
            user.name = name

        if(password != null)
            user.pw = password

        if(githubId != null)
            user.githubId = password

        return usersRepo.save(user)
    }

    override fun deleteUser(userId: Long): User {
        val user = getUserById(userId)

        Hibernate.initialize(user.bbItems)
        Hibernate.initialize(user.boards)
        Hibernate.initialize(user.comments)
        Hibernate.initialize(user.forumItems)
        Hibernate.initialize(user.blackboardsSettings)

        usersRepo.delete(user)
        return user
    }

    override fun getBoardMembers(boardId: Long): Iterable<User>{
        val board = boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        return usersRepo.findByBoards(board).asIterable()
    }

}