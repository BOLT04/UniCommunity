package isel.pt.unicommunity.repository.network

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import isel.pt.unicommunity.BASEURL
import isel.pt.unicommunity.model.NavLink
import java.nio.charset.Charset
import java.util.*




class BasicAuthNavLinkGetRequest<T>(
    navLink : NavLink<T>,
    onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    email :String,
    password : String,
    headers: MutableMap<String, String>? = null,
    logger: (() -> Unit)? = null
): BasicAuthenticationGetRequest<T>(
    navLink.responseClass,
    navLink.href,
    onSuccessListener,
    onErrorListener,
    email,
    password,
    headers,
    logger
)

class NavLinkGetRequest<T>(
    navLink : NavLink<T>,
    onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    headers: MutableMap<String, String>? = null,
    logger: (() -> Unit)? = null
): GetRequest<T>(
    navLink.responseClass,
    navLink.href,
    onSuccessListener,
    onErrorListener,
    headers,
    logger
)




open class BasicAuthenticationGetRequest<T>(
    clazz: Class<T>,
    url: String,
    onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    email :String,
    password : String,
    headers: MutableMap<String, String>? = null,
    logger: (() -> Unit)? = null
): GetRequest<T>(
    clazz, url, onSuccessListener, onErrorListener, basicAuthenticationMiddleware(headers, email, password), logger
)






open class GetRequest<T>(
    private val clazz: Class<T>,
    url: String,
    private val onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    private val headers: MutableMap<String, String>? = null,
    private val logger: (() -> Unit)? = null) : Request<T>(Method.GET, checkUrl(url), onErrorListener) {

    init {
        this.setShouldCache(false) //todo development only take this out of here
    }

    override fun deliverResponse(response: T?) {
        if(response!=null)
            onSuccessListener.onResponse(response) //todo seems weird
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {

        val parsedNetworkResponseContent = parseNetworkResponseContentJackson(clazz, response)

        logger?.invoke()

        return Response.success(parsedNetworkResponseContent, HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun getHeaders(): MutableMap<String, String> {
        return headers ?: super.getHeaders()
    }
}





