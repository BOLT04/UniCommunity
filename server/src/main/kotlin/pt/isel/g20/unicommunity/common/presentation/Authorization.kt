package pt.isel.g20.unicommunity.common.presentation

import org.springframework.http.HttpStatus
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.ExtendedProblemJson
import pt.isel.g20.unicommunity.hateoas.Link
import pt.isel.g20.unicommunity.hateoas.ProblemJson

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
/**
 * Annotation used to signal that a given controller action requires authentication
 */
annotation class AuthorizationRequired
annotation class AuthorizationOptional

/**
 * Annotations used to signal that a given controller action requires a certain role
 */
annotation class TeacherRoleRequired
annotation class StudentRoleRequired

/**
 * Exception thrown whenever an access to a resource requires authorization and the required credentials have not
 * been presented.
 */
open class AuthorizationException : Exception()

/**
 * Role exceptions
 */
open class TeacherRoleRequiredException : Exception()
open class StudentRoleRequiredException : Exception()

fun authorizationProblemJson(): ExtendedProblemJson =
        ExtendedProblemJson(
                title = "Authorization required",
                detail = "Access was denied because the required authorization was not granted",
                status = HttpStatus.UNAUTHORIZED.value(),
                links = mutableMapOf(
                        Rels.LOGIN to Link(Uri.LOGIN_ROUTE)
                )
        )

fun forbiddenProblemJson(title: String? = "Forbidden" ): ProblemJson =
        ProblemJson(
                title = title,
                detail = "Access was denied because the authenticated user on the request does not meet the role requirement",
                status = HttpStatus.FORBIDDEN.value()
        )