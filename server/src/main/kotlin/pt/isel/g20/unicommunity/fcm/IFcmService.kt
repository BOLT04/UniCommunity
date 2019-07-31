package pt.isel.g20.unicommunity.fcm

import com.fasterxml.jackson.annotation.JsonCreator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.*

/**
 * A successful response to the endpoint 'Create relationship maps for app instances' of the
 * Instance ID API, is an empty object.
 * On the link below check the section 'Create relationship maps for app instances'
 * @see [Docs](https://developers.google.com/instance-id/reference/server)
 */
class Empty @JsonCreator constructor()

const val API_KEY = "AAAAO9WxOVo:APA91bEJddWBBh80oig0ua9xKwy1cyrDwo66BumPOkkvprGFVbALaPGmGlHaAGXdqr73aG1_7XOH1T-Yd-crngtk7dU1zkJoBbej1WDX4cGhUku8YvlDH0FefuU589vHRqMkhWBJIDMQ"

interface IFcmService {
    @Headers("Authorization: key=${API_KEY}")
    @POST("iid/v1/{registrationToken}/rel/topics/{topicName}")
    suspend fun subscribeAppToTopic(
            @Path("registrationToken", encoded= true) registrationToken: String,
            @Path("topicName", encoded= true) topicName: String
    ): Response<Empty>
}

object FcmServiceFactory {
    private const val BASE_URL = "https://iid.googleapis.com/"

    fun makeFcmServiceService(): IFcmService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(IFcmService::class.java)
    }
}