package pt.isel.g20.unicommunity.auth

import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.g20.unicommunity.common.presentation.AuthorizationException
import pt.isel.g20.unicommunity.common.presentation.TeacherRoleRequired
import pt.isel.g20.unicommunity.common.presentation.TeacherRoleRequiredException
import pt.isel.g20.unicommunity.user.model.User
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * This interceptor is located after the AuthorizationInterceptor in the chain.
 * Request handling interceptor used to check whether the user belongs to the list of members of a given project,
 * giving him access to the project resource.
 * @see pt.isel.g20.unicommunity.config.AuthorizationInterceptor
 */
class TeacherRoleInterceptor() : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if(handler is HandlerMethod && handler.hasMethodAnnotation(TeacherRoleRequired::class.java)){
            val userAttribute = request.session.getAttribute("user") ?: throw AuthorizationException()
            val user = userAttribute as User
            if (user.role != "TEACHER")
                throw TeacherRoleRequiredException()
        }

        return true
    }
}
