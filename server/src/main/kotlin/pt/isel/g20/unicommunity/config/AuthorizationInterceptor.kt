package pt.isel.g20.unicommunity.config

import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.g20.unicommunity.auth.service.IAuthService
import pt.isel.g20.unicommunity.common.presentation.AuthorizationException
import pt.isel.g20.unicommunity.common.presentation.AuthorizationOptional
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.user.model.User
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * Request handling interceptor used to check whether the authorization requirements are met.
 */
class AuthorizationInterceptor(
        private val authService: IAuthService
) : HandlerInterceptor {

    private val decoder = Base64.getDecoder()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if(handler is HandlerMethod){

            val optional = handler.hasMethodAnnotation(AuthorizationOptional::class.java)
            val toProcess = optional || handler.hasMethodAnnotation(AuthorizationRequired::class.java)

            if(toProcess) {
                val authHeaderList =
                        request.getHeader("Authorization")?.split(" ")

                if(authHeaderList == null || authHeaderList.isEmpty()){
                    if(optional)
                        addToModelUserDetails(request.session, null)
                    else
                        throw AuthorizationException()
                }
                else when (authHeaderList[0]) {
                        "Basic" -> processBasicAuthorization(authHeaderList[1], request)
                }
            }
        }
        return true
    }

    /**
     * @param encodedCred The enconded credentials in Base64 that came in the request header Authorization
     */

    private fun processBasicAuthorization(encodedCred: String, request: HttpServletRequest) {
        val credBytes = decoder.decode(encodedCred)
        try {
            val credentials = String(credBytes, Charset.forName("UTF-8"))
            val credParts = credentials.split(":")
            if (credParts.size != 2)
                throw AuthorizationException()
            val username = credParts[0]
            val password = credParts[1]

            try {
                val user = authService.authenticate(username, password)
                addToModelUserDetails(request.session, user)
            } catch (e: Exception) {
                throw AuthorizationException()
            }
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    private fun addToModelUserDetails(session: HttpSession, user: User?) {
        session.setAttribute("user", user)
    }
}


