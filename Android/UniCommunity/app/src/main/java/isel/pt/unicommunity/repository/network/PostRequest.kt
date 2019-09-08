package isel.pt.unicommunity.repository.network

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import isel.pt.unicommunity.model.BodyNavLink
import java.nio.charset.Charset




class BasicAuthNavLinkPostRequest<O,I>(
    bodyNavLink: BodyNavLink<O, I>,
    body: O?,
    onSuccessListener: Response.Listener<I>,
    onErrorListener: Response.ErrorListener,
    email: String,
    password: String,
    headers: MutableMap<String, String>? = null,
    logger: ((String) -> Unit)? = null
):BasicAuthenticationPostRequest<O,I>(
    bodyNavLink.responseClass,
    bodyNavLink.href,
    body,
    onSuccessListener, onErrorListener, email, password, headers, logger
)

class LinkPostRequest<O,I>(
    bodyNavLink: BodyNavLink<O, I>,
    body: O?,
    onSuccessListener: Response.Listener<I>,
    onErrorListener: Response.ErrorListener,
    headers: MutableMap<String, String>? = null,
    logger: ((String) -> Unit)? = null
):PostRequest <O,I>(
    bodyNavLink.responseClass,
    checkUrl(bodyNavLink.href),
    body,
    onSuccessListener, onErrorListener, headers, logger
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
    logger: ((String) -> Unit)?

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
    private val logger: ((String) -> Unit)? = null) : Request<I>(Method.POST, checkUrl(url), onErrorListener) {

    init {
        this.setShouldCache(false)
    }

    override fun deliverResponse(response: I?) {
        if(response!=null)
            onSuccessListener.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<I> {

        response?.data?.let { logger?.invoke(String(it)) }

        val parsedNetworkResponseContent = parseNetworkResponseContentJackson(iClazz, response)

        logger?.invoke(parsedNetworkResponseContent.toString())

        return Response.success(parsedNetworkResponseContent, HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun getHeaders(): MutableMap<String, String> {
        return headers ?: super.getHeaders()
    }

    override fun getBody(): ByteArray {
        if(body==null)
            return ByteArray(0)
        Log.v("POST", mapper.writeValueAsString(body))
        return mapper.writeValueAsString(body).toByteArray(Charset.forName("UTF-8"))
    }

    override fun getBodyContentType(): String {
        return "application/json"
    }


}