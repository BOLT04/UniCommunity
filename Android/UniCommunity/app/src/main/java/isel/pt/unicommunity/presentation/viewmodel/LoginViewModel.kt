package isel.pt.unicommunity.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.VolleyError
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.firebase.messaging.FirebaseMessaging
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.toBlackBoardSettingsCollection
import isel.pt.unicommunity.model.links.BlackBoardSettingsLink
import isel.pt.unicommunity.model.links.LoginInputDto
import isel.pt.unicommunity.model.links.LoginLink
import isel.pt.unicommunity.model.links.LoginOutputDto
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkGetRequest
import isel.pt.unicommunity.repository.network.LinkPostRequest
import java.util.logging.Handler

class LoginViewModel(private val app : UniCommunityApp, private val loginLink : LoginLink) : ViewModel() {

    val loginLd = ErrorHandlingMLD<LoginInputDto, String>()


    fun tryLogin(email : String, pw :String){

        val loginRequest = LinkPostRequest(
            loginLink,
            LoginOutputDto(email,pw),
            Response.Listener {
                loginLd.success(it)
            },
            Response.ErrorListener {

                val handle = VolleyErrorHandler.handle(it)

                loginLd.error( VolleyErrorHandler.getMessage(it) )
            }
        )

        app.queue.add(loginRequest)
    }


    fun subscribeToBoards(blackBoardSettingsLink: BlackBoardSettingsLink) {
        val req = BasicAuthNavLinkGetRequest(
            blackBoardSettingsLink,
            Response.Listener { collectionJson ->

                val fcm = FirebaseMessaging.getInstance()
                collectionJson.toBlackBoardSettingsCollection().blackBoardseSttings.forEach {
                    fcm.subscribeToTopic(it.bbFcmTopicName)
                }
            },
            Response.ErrorListener {  },
            app.email,
            app.password
        )

        app.queue.add(req)
    }

}

open class Error(
    val title: String
)

class ProblemJson(
    val title: String,
    val detail: String,
    val status: Int
)

class VolleyErrorHandler {

    companion object {
        private val mapper = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


        fun handle(err: VolleyError): Error {

            val message = err.message
            if(message != null)
                return Error(message)


            val content = String(err.networkResponse.data)
            val prblmJson = mapper.readValue(content, ProblemJson::class.java).title
            return Error(prblmJson)
        }

        fun getMessage(err: VolleyError) = handle(err).title

    }
}



