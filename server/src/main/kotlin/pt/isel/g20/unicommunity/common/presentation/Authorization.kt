package pt.isel.g20.unicommunity.common.presentation

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