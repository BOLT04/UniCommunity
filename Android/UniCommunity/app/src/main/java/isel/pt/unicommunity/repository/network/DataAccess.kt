package isel.pt.unicommunity.repository.network

import com.android.volley.ParseError
import org.json.JSONException
import org.json.JSONArray
import android.R.attr.data
import com.android.volley.Cache
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class DataAccess {



    fun withCache(response : NetworkResponse) : Response<Any>{


        try {
            var cacheEntry: Cache.Entry? = HttpHeaderParser.parseCacheHeaders(response)
            if (cacheEntry == null) {
                cacheEntry = Cache.Entry()
            }
            val cacheHitButRefreshed =
                (3 * 60 * 1000).toLong() // in 3 minutes cache will be hit, but also refreshed on background
            val cacheExpired = (24 * 60 * 60 * 1000).toLong() // in 24 hours this cache entry expires completely
            val now = System.currentTimeMillis()
            val softExpire = now + cacheHitButRefreshed
            val ttl = now + cacheExpired
            cacheEntry.data = response.data
            cacheEntry.softTtl = softExpire
            cacheEntry.ttl = ttl

            var headerValue: String?
            headerValue = response.headers.get("Date")
            if (headerValue != null) {
                cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue)
            }
            headerValue = response.headers.get("Last-Modified")
            if (headerValue != null) {
                cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue)
            }
            cacheEntry.responseHeaders = response.headers
            val jsonString = String(
                response.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response.headers)))

            return Response.success(JSONArray(jsonString), cacheEntry)
        } catch (e: UnsupportedEncodingException) {
            return Response.error(ParseError(e))
        } catch (e: JSONException) {
            return Response.error(ParseError(e))
        }


    }
}