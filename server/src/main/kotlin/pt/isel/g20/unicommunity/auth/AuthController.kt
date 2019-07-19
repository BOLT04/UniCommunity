package pt.isel.g20.unicommunity.auth

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.auth.model.LoginDto
import pt.isel.g20.unicommunity.auth.model.LoginResponse
import pt.isel.g20.unicommunity.auth.service.IAuthService
import pt.isel.g20.unicommunity.common.APPLICATION_HAL_JSON
import pt.isel.g20.unicommunity.common.APPLICATION_JSON
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.presentation.authorizationProblemJson
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(
        produces = [APPLICATION_HAL_JSON, APPLICATION_JSON])
class AuthenticationController(private val service: IAuthService) {
    @PostMapping(Uri.LOGIN_ROUTE)
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<LoginResponse> =
            service.authenticate(loginDto.email, loginDto.password).let {
                val response = LoginResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    // Used in AuthenticationController
    @ExceptionHandler
    fun handleNotFoundUserException(e: NotFoundUserException) =
            ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(authorizationProblemJson())
}