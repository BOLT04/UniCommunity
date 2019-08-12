package isel.pt.unicommunity.repository.network

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import isel.pt.unicommunity.model.webdto.BodyNavLink
import java.nio.charset.Charset

private val gson = Gson()


class NavLinkPostRequest<O,I>(
    bodyNavLink: BodyNavLink<O,I>,
    body: O?,
    onSuccessListener: Response.Listener<I>,
    onErrorListener: Response.ErrorListener,
    email: String,
    password: String,
    headers: MutableMap<String, String>? = null,
    logger: (() -> Unit)? = null
):BasicAuthenticationPostRequest<O,I>(
    bodyNavLink.responseClass,
    bodyNavLink.href,
    body,
    onSuccessListener, onErrorListener, email, password, headers, logger

)

open class BasicAuthenticationPostRequest<O,I>(
    iClazz: Class<I>,
    url: String,
    body: O?,
    onSuccessListener: Response.Listener<I>,
    onErrorListener: Response.ErrorListener,
    email: String,
    password: String,
    headers: MutableMap<String, String>?,
    logger: (() -> Unit)?

): PostRequest<O,I>(
    iClazz, checkUrl(url), body, onSuccessListener, onErrorListener, basicAuthenticationMiddleware(headers, email, password), logger
)

open class PostRequest<O,I>(
    private val iClazz: Class<I>,
    url: String,
    private val body: O?,
    private val onSuccessListener: Response.Listener<I>,
    onErrorListener: Response.ErrorListener,
    private val headers: MutableMap<String, String>? = null,
    private val logger: (() -> Unit)? = null) : Request<I>(Method.POST, checkUrl(url), onErrorListener) {

    init {
        this.setShouldCache(false) //todo development only take this out of here
    }

    val TAG = "GetRequest"

    override fun deliverResponse(response: I?) {
        if(response!=null)
            onSuccessListener.onResponse(response) //todo seems weird
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<I> {

        val parsedNetworkResponseContent = parseNetworkResponseContentJackson(iClazz, response)

        logger?.invoke()

        return Response.success(parsedNetworkResponseContent, HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun getHeaders(): MutableMap<String, String> {
        return headers ?: super.getHeaders()
    }

    override fun getBody(): ByteArray {
        if(body==null)
            return super.getBody()
        return gson.toJson(body).toByteArray(Charset.forName("UTF-8"))
    }

    override fun getBodyContentType(): String {
        return if(body!=null) "application/json" else super.getBodyContentType()
    }


}