package pt.isel.g20.unicommunity.common

import org.springframework.http.CacheControl
import org.springframework.http.ResponseEntity
import java.net.URI
import java.util.concurrent.TimeUnit

fun cacheOkResponse(responseBody: Any) =
        ResponseEntity
                .ok()
                .cacheControl(
                        CacheControl
                                .maxAge(1, TimeUnit.HOURS)
                                .cachePrivate())
                .eTag(responseBody.hashCode().toString())
                .body(responseBody)


fun cacheCreatedResponse(responseBody: Any, resourceHref: URI) =
        ResponseEntity
                .created(resourceHref)
                .cacheControl(
                        CacheControl
                                .maxAge(1, TimeUnit.HOURS)
                                .cachePrivate())
                .eTag(responseBody.hashCode().toString())
                .body(responseBody)
