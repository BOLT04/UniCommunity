package pt.isel.g20.unicommunity.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.g20.unicommunity.auth.service.AuthService
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.presentation.AuthorizationException
import pt.isel.g20.unicommunity.hateoas.ExtendedProblemJson
import pt.isel.g20.unicommunity.hateoas.Link
import pt.isel.g20.unicommunity.hateoas.ProblemJson

@Configuration
@ControllerAdvice
@EnableWebMvc
class InterceptorConfig(val authService: AuthService) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthorizationInterceptor(authService))
    }

    /**
     * Globally applicable exception handler
     */
    @ExceptionHandler
    fun handleInvalidAuthorization(e: AuthorizationException): ResponseEntity<ProblemJson> {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ExtendedProblemJson(
                        title = "Authorization required",
                        detail = "Access was denied because the required authorization was not granted",
                        status = HttpStatus.UNAUTHORIZED.value(),
                        links = mutableMapOf(
                                Rels.LOGIN to Link(Uri.LOGIN_ROUTE)
                        )
                ))
    }
}