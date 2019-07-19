package isel.pt.unicommunity.repository.network

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import java.nio.charset.Charset

private val gson = Gson()

open class PostRequest<I,O>(
    private val oClazz: Class<O>,
    url: String,
    private val body: I?,
    private val onSuccessListener: Response.Listener<O>,
    onErrorListener: Response.ErrorListener,
    private val headers: MutableMap<String, String>? = null,
    private val logger: (() -> Unit)? = null) : Request<O>(Method.POST, url, onErrorListener) {


    val TAG = "GetRequest"

    override fun deliverResponse(response: O?) {
        if(response!=null)
            onSuccessListener.onResponse(response) //todo seems weird
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<O> {

        val parsedNetworkResponseContent = parseNetworkResponseContent(oClazz, response)

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