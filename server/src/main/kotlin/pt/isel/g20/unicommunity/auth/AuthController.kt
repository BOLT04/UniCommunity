package pt.isel.g20.unicommunity.auth

import org.springframework.http.CacheControl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.g20.unicommunity.auth.model.LoginDto
import pt.isel.g20.unicommunity.auth.model.LoginResponse
import pt.isel.g20.unicommunity.auth.service.IAuthService
import pt.isel.g20.unicommunity.common.APPLICATION_HAL_JSON
import pt.isel.g20.unicommunity.common.APPLICATION_JSON
import pt.isel.g20.unicommunity.common.Uri
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(
        produces = [APPLICATION_HAL_JSON, APPLICATION_JSON])
class AuthenticationController(private val service: IAuthService) {
    @PostMapping(Uri.LOGIN_ROUTE+"a")//TODO: find a way to remove /login of spring security to allow our /login instead of /logina
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
}