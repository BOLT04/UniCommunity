package pt.isel.g20.unicommunity.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.g20.unicommunity.auth.service.IAuthService
import pt.isel.g20.unicommunity.common.presentation.AuthorizationException
import pt.isel.g20.unicommunity.common.presentation.AuthorizationOptional
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.user.model.User
import pt.isel.g20.unicommunity.user.service.IUserService
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * Request handling interceptor used to check whether the authorization requirements are met.
 */
class AuthorizationInterceptor(
        private val authService: IAuthService,
        private val userService: IUserService
) : HandlerInterceptor {

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
                    "Basic"  -> processBasicAuthorization(authHeaderList[1], request)
                    "Bearer" -> processBearerAuthorization(authHeaderList[1], request)
                }
            }
        }
        return true
    }

    private val decoder = Base64.getDecoder()

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

    private fun processBearerAuthorization(accessToken: String, request: HttpServletRequest) {
        val token = sendPost(accessToken)
        if (!token.active)
            throw AuthorizationException()
        val user = userService.getUserByName(token.user_id)
        addToModelUserDetails(request.session, user)
    }

    private fun addToModelUserDetails(session: HttpSession, user: User?) {
        session.setAttribute("user", user)
    }

    //TODO: Refactor this code to use an HTTP library instead
    private val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun sendPost(token: String): TokenIntrospect {
        val url = URL("http://35.242.140.186/openid-connect-server-webapp/introspect")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "POST"
            val userCredentials = "caf3021e-1da6-41e6-9d38-d994fe7bb2a0:NavdJNvyaDNYi6beGakeaJrFLytxKoG4NxcxtA77nR-PKxfOLDBPsmXQlsVAIuFpaALEZA5W-GrvwH6v_TPkJQ"
            val basicAuth = "Basic " + String(Base64.getEncoder().encode(userCredentials.toByteArray()))

            this.setRequestProperty("Authorization", basicAuth)
            this.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded")
            val body = "token=$token"
            doOutput = true
            this.outputStream.write(body.toByteArray())
            println("\nSent 'POST' request to URL : $url; Response Code : $responseCode")
            val json = inputStream.bufferedReader().readLine()

            return objectMapper.readValue(json, TokenIntrospect::class.java)
        }
    }

    class TokenIntrospect(
        val active: Boolean = false,
        val scope: String = "",
        val user_id: String = ""
    )
}


