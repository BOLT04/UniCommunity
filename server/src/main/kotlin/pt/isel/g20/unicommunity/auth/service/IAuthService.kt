package pt.isel.g20.unicommunity.auth.service

import pt.isel.g20.unicommunity.common.BadAuthenticationException
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.user.model.User

interface IAuthService {
    @Throws(NotFoundUserException::class, BadAuthenticationException::class)
    fun authenticate(email: String, password: String) : User
}