package pt.isel.g20.unicommunity.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.g20.unicommunity.auth.service.IAuthService
import pt.isel.g20.unicommunity.common.presentation.AuthorizationException
import pt.isel.g20.unicommunity.common.presentation.authorizationProblemJson
import pt.isel.g20.unicommunity.hateoas.ProblemJson
import pt.isel.g20.unicommunity.user.service.IUserService

@Configuration
@ControllerAdvice
@EnableWebMvc
class InterceptorConfig(val authService: IAuthService, val userService: IUserService) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthorizationInterceptor(authService, userService))
    }
//TODO: set the default page start count to be '1' instead of '0'
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        val resolver = PageableHandlerMethodArgumentResolver()
        resolver.setOneIndexedParameters(true)
        resolvers.add(resolver)
    }

    /**
     * Globally applicable exception handler
     */
    @ExceptionHandler
    fun handleInvalidAuthorization(e: AuthorizationException): ResponseEntity<ProblemJson> =
        ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(authorizationProblemJson())
}