package isel.pt.unicommunity.repository.network

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import java.nio.charset.Charset
import java.util.*


/*
/**
 * logger parameter is called after the jackson mapper as finished reading the value.
 */
class GetRequest1<T>(private val type : Class<T>, url: String, success: Response.Listener<T>, error: Response.ErrorListener,
                    private val headers: MutableMap<String, String>?,
                    private val logger: (() -> Unit)? = null)
    : JsonRequest<T>(Method.GET, url, "", success, error) {

    val TAG = "GetRequest"

    override fun parseNetworkResponseContent(response: NetworkResponse): Response< T> {
        val dataStr = String(response.data)
        Log.v(TAG, "parsing network response $dataStr")

        val mapper = jacksonObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val resDto = mapper.readValue(dataStr, type ) as T

        logger?.invoke()

        val parseCacheHeaders = HttpHeaderParser.parseCacheHeaders(response)
        return Response.success(resDto, parseCacheHeaders)
    }

    override fun getHeaders(): MutableMap<String, String> {
        return headers ?: super.getHeaders()
    }
}

inline fun <reified T: Any> getRequestOf(
        url: String,
        success: Response.Listener<T>,
        error: Response.ErrorListener,
        headers: MutableMap<String, String>?) =
    GetRequest(T::class.java, url, success, error, headers)


*/





class BasicAuthenticationGetRequest<T>(
    clazz: Class<T>,
    url: String,
    onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    username :String,
    password : String,
    headers: MutableMap<String, String>? = null,
    logger: (() -> Unit)? = null
): GetRequest<T>(
    clazz, url, onSuccessListener, onErrorListener, basicAuthenticationMiddleware(headers, username, password), logger
)


private val encoder = Base64.getEncoder()

fun basicAuthenticationMiddleware(mutableMap: MutableMap<String,String>?, username: String, password: String): MutableMap<String, String> {

    val mm = mutableMap ?: mutableMapOf()
    val toByteArray = "$username:$password".toByteArray(Charset.forName("UTF-8"))
    val code = encoder.encodeToString(toByteArray)

    mm["Authorization"] = ("Basic $code")

    return mm

}



open class GetRequest<T>(
    private val clazz: Class<T>,
    url: String,
    private val onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    private val headers: MutableMap<String, String>? = null,
    private val logger: (() -> Unit)? = null) : Request<T>(Method.GET, url, onErrorListener) {



    val TAG = "GetRequest"

    override fun deliverResponse(response: T?) {
        if(response!=null)
            onSuccessListener.onResponse(response) //todo seems weird
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {

        val parsedNetworkResponseContent = parseNetworkResponseContent(clazz, response)

        logger?.invoke()

        return Response.success(parsedNetworkResponseContent, HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun getHeaders(): MutableMap<String, String> {
        return headers ?: super.getHeaders()
    }
}

class ErrorResponse(val networkResponse: NetworkResponse){

    fun getParsedError() = parseNetworkResponseContent(Error::class.java, networkResponse)

}

class Error(val tile:String, val detail:String, val status: Int)


private val gson = Gson()
fun<T> parseNetworkResponseContent(clazz: Class<T>, response: NetworkResponse?): T {
    val json = String(
        response?.data ?: ByteArray(0),
        Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))

    return gson.fromJson(json, clazz)
}