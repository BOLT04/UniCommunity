package pt.isel.g20.unicommunity.features.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.presentation.authorizationProblemJson
import pt.isel.g20.unicommunity.features.auth.model.LoginDto
import pt.isel.g20.unicommunity.features.auth.model.LoginResponse
import pt.isel.g20.unicommunity.features.auth.service.AuthService

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON])
class AuthenticationController(private val service: AuthService) {
    @PostMapping(Uri.LOGIN_ROUTE)
    fun login(@RequestBody loginDto: LoginDto) =
            okResponse(
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