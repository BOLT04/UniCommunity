package pt.isel.g20.unicommunity.common

import org.springframework.http.ResponseEntity
import java.net.URI

fun okResponse(responseBody: Any) =
        ResponseEntity
                .ok()
                .body(responseBody)


fun createdResponse(responseBody: Any, resourceHref: URI) =
        ResponseEntity
                .created(resourceHref)
                .body(responseBody)
