package pt.isel.g20.unicommunity.features.fcm

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

const val API_KEY = "AAAAO9WxOVo:APA91bEJddWBBh80oig0ua9xKwy1cyrDwo66BumPOkkvprGFVbALaPGmGlHaAGXdqr73aG1_7XOH1T-Yd-crngtk7dU1zkJoBbej1WDX4cGhUku8YvlDH0FefuU589vHRqMkhWBJIDMQ"

interface IiidService {
    /**
     * Subscribe an FCM app instance of a web client to a topic.
     */
    @Headers("Authorization: key=${API_KEY}")
    @POST("iid/v1/{registrationToken}/rel/topics/{topicName}")
    suspend fun subscribeAppToTopic(
            @Path("registrationToken", encoded= true) registrationToken: String,
            @Path("topicName", encoded= true) topicName: String
    ): Response<Empty>

    @Headers("Authorization: Bearer ${API_KEY}")
    @DELETE("v1/web/iid/{registrationToken}")
    suspend fun unsubscribeAppFromTopic(
            @Path("registrationToken", encoded= true) registrationToken: String
    ): Response<Empty>
}

interface IFcmService {
    @Headers("Authorization: key=$API_KEY")
    @POST("fcm/send")
    suspend fun sendMessageToTopic(@Body body: FcmMessage): Response<SuccessMessage>
}

object GoogleServiceFactory {
    private const val GOOGLE_IID_API_BASE_URL = "https://iid.googleapis.com/"
    private const val GOOGLE_FCM_API_BASE_URL = "https://fcm.googleapis.com/"

    fun makeFcmService(): IFcmService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        return Retrofit.Builder()
                .baseUrl(GOOGLE_FCM_API_BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(IFcmService::class.java)
    }

    fun makeIidService(): IiidService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        return buildRetrofitService(GOOGLE_IID_API_BASE_URL, client, IiidService::class.java)
    }
}

fun <T> buildRetrofitService(baseUrl: String, client: OkHttpClient, service: Class<T>) =
        Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(service)