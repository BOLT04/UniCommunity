package pt.isel.g20.unicommunity.user.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.user.exception.InvalidUserEmailException
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException
import pt.isel.g20.unicommunity.user.model.User

@Service
class UserService(val usersRepo: UserRepository) : IUserService {
    override fun getAllUsers(): Iterable<User> = usersRepo.findAll()

    override fun getUserById(userId: Long) = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

    override fun createUser(name: String, email: String, password: String, githubId: String?): User {
        if (getAllUsers().map { it.email }.contains(email)) {
            throw InvalidUserEmailException()
        }

        val user =
                if(githubId != null)
                    User(name, email, password, "TEACHER", githubId)
                else
                    User(name, email, "TEACHER", password)

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
        usersRepo.delete(user)
        return user
    }

}