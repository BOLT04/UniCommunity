package isel.pt.unicommunity.repository.network

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import isel.pt.unicommunity.model.NavLink



class BasicAuthNavLinkDeleteRequest<T>(
    navLink : NavLink<T>,
    onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    email :String,
    password : String,
    headers: MutableMap<String, String>? = null,
    logger: (() -> Unit)? = null
): BasicAuthenticationDeleteRequest<T>(
    navLink.responseClass,
    navLink.href,
    onSuccessListener,
    onErrorListener,
    email,
    password,
    headers,
    logger
)

class NavLinkDeleteRequest<T>(
    navLink : NavLink<T>,
    onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    headers: MutableMap<String, String>? = null,
    logger: (() -> Unit)? = null
): DeleteRequest<T>(
    navLink.responseClass,
    navLink.href,
    onSuccessListener,
    onErrorListener,
    headers,
    logger
)


open class BasicAuthenticationDeleteRequest<T>(
    clazz: Class<T>,
    url: String,
    onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    email :String,
    password : String,
    headers: MutableMap<String, String>? = null,
    logger: (() -> Unit)? = null
): DeleteRequest<T>(
    clazz, url, onSuccessListener, onErrorListener, basicAuthenticationMiddleware(headers, email, password), logger
)



open class DeleteRequest<T>(
    private val clazz: Class<T>,
    url: String,
    private val onSuccessListener: Response.Listener<T>,
    onErrorListener: Response.ErrorListener,
    private val headers: MutableMap<String, String>? = null,
    private val logger: (() -> Unit)? = null) : Request<T>(Method.DELETE, checkUrl(url), onErrorListener) {

    init {
        this.setShouldCache(false)
    }

    override fun deliverResponse(response: T?) {
        if(response!=null)
            onSuccessListener.onResponse(response)
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
