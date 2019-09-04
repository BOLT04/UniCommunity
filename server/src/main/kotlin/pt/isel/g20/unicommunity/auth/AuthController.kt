package pt.isel.g20.unicommunity.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.auth.model.LoginDto
import pt.isel.g20.unicommunity.auth.model.LoginResponse
import pt.isel.g20.unicommunity.auth.service.AuthService
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.presentation.authorizationProblemJson

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON])
class AuthenticationController(private val service: AuthService) {
    @PostMapping(Uri.LOGIN_ROUTE)
    fun login(@RequestBody loginDto: LoginDto) =
            cacheOkResponse(
                    LoginResponse(
                            service.authenticate(loginDto.email, loginDto.password)
                    )
            )

    // Used in AuthenticationController
    @ExceptionHandler
    fun handleNotFoundUserException(e: NotFoundUserException) =
            ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(authorizationProblemJson())
}