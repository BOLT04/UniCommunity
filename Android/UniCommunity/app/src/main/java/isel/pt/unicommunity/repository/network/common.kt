package isel.pt.unicommunity.repository.network

import com.android.volley.NetworkResponse
import com.android.volley.toolbox.HttpHeaderParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import isel.pt.unicommunity.BASEURL
import java.nio.charset.Charset

fun checkUrl(href:String)= if(href.contains("http://")) href else "$BASEURL$href"


val mapper : ObjectMapper = jacksonObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

fun<T> parseNetworkResponseContentJackson(clazz: Class<T>, response: NetworkResponse?): T {

    val json = String(
        response?.data ?: ByteArray(0),
        Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))

    return  mapper.readValue(json, clazz) as T
}