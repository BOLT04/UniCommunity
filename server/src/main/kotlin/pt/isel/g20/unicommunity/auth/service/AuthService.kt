package pt.isel.g20.unicommunity.auth.service

import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.BadAuthenticationException
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.user.model.User

@Service
class AuthService(
        val userRepo: UserRepository
) : IAuthService {

    override fun authenticate(email: String, password: String): User {
        val user = userRepo.findByEmail(email)?: throw NotFoundUserException()

        if (user.pw == password)
            return user
        else
            throw BadAuthenticationException()
    }
}