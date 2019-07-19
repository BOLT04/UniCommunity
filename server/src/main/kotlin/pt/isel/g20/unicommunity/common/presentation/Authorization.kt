package pt.isel.g20.unicommunity.common.presentation

import org.springframework.http.HttpStatus
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.ExtendedProblemJson
import pt.isel.g20.unicommunity.hateoas.Link

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
/**
 * Annotation used to signal that a given controller action requires authentication
 */
annotation class AuthorizationRequired
annotation class AuthorizationOptional

/**
 * Exception thrown whenever an access to a resource requires authorization and the required credentials have not
 * been presented.
 */
open class AuthorizationException : Exception()

fun authorizationProblemJson(): ExtendedProblemJson =
        ExtendedProblemJson(
                title = "Authorization required",
                detail = "Access was denied because the required authorization was not granted",
                status = HttpStatus.UNAUTHORIZED.value(),
                links = mutableMapOf(
                        Rels.LOGIN to Link(Uri.LOGIN_ROUTE)
                )
        )