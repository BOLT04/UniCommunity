package isel.pt.unicommunity.repository.network

import com.android.volley.NetworkResponse
import com.android.volley.toolbox.HttpHeaderParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import isel.pt.unicommunity.BASEURL
import java.nio.charset.Charset
import java.util.*

fun checkUrl(href:String)= if(href.contains("http://")) href else "$BASEURL$href"


val mapper : ObjectMapper = jacksonObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

fun<T> parseNetworkResponseContentJackson(clazz: Class<T>, response: NetworkResponse?): T {

    val json = String(
        response?.data ?: ByteArray(0),
        Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))

    return  mapper.readValue(json, clazz) as T
}

private val encoder = Base64.getEncoder()

fun basicAuthenticationMiddleware(mutableMap: MutableMap<String,String>?, username: String, password: String): MutableMap<String, String> {

    val mm = mutableMap ?: mutableMapOf()
    val toByteArray = "$username:$password".toByteArray(Charset.forName("UTF-8"))
    val code = encoder.encodeToString(toByteArray)

    mm["Authorization"] = ("Basic $code")

    return mm

}