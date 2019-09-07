package pt.isel.g20.unicommunity.common

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionResolver : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundUserException::class)
    fun handleNotFoundUser(
            ex: NotFoundUserException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "User was not found",
                detail = "The specified user was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotFoundUsersBlackboardsException::class)
    fun handleNotFoundUsersBlackboards(
            ex: NotFoundUsersBlackboardsException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "User's blackboard config was not found",
                detail = "The specified user blackboard config was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotFoundTemplateException::class)
    fun handleNotFoundTemplate(
            ex: NotFoundTemplateException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Template was not found",
                detail = "The specified template was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotFoundBoardException::class)
    fun handleNotFoundBoard(
            ex: NotFoundBoardException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Board was not found",
                detail = "The specified board was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotFoundBlackboardException::class)
    fun handleNotFoundBlackboard(
            ex: NotFoundBlackboardException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Blackboard was not found",
                detail = "The specified blackboard was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotFoundBlackboardItemException::class)
    fun handleNotFoundBlackboardItem(
            ex: NotFoundBlackboardItemException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Blackboard item was not found",
                detail = "The specified blackboard item was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotFoundForumException::class)
    fun handleNotFoundForum(
            ex: NotFoundForumException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Forum was not found",
                detail = "The specified forum was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotFoundForumItemException::class)
    fun handleNotFoundForumItem(
            ex: NotFoundForumItemException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Forum item was not found",
                detail = "The specified forum item was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotFoundCommentException::class)
    fun handleNotFoundComment(
            ex: NotFoundCommentException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Comment was not found",
                detail = "The specified comment was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotFoundReportException::class)
    fun handleNotFoundReport(
            ex: NotFoundReportException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Report was not found",
                detail = "The specified report was not found",
                status = HttpStatus.NOT_FOUND.value()
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(BadAuthenticationException::class)
    fun handleBadAuthentication(
            ex: BadAuthenticationException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Authentication failed",
                detail = "The authentication was unsuccessful because of wrong credentials",
                status = HttpStatus.UNAUTHORIZED.value()
        )

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(InvalidUserEmailException::class)
    fun handleInvalidUserEmail(
            ex: InvalidUserEmailException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Invalid email",
                detail = "An error occurred because the specified email is invalid",
                status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(InvalidNotificationLevelException::class)
    fun handleInvalidNotificationLevel(
            ex: InvalidNotificationLevelException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Invalid notification level",
                detail = "An error occurred because the specified email is invalid",
                status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(InvalidUserRoleException::class)
    fun handleInvalidUserRole(
            ex: InvalidUserRoleException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Invalid user role",
                detail = "An error occurred because the specified role for the user doesnt exist",
                status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(InvalidReportTypeException::class)
    fun handleReportType(
            ex: InvalidReportTypeException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Invalid request type",
                detail = "An error occurred because the specified request type is invalid",
                status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(InvalidTemplateConfigurationException::class)
    fun handleInvalidTemplateConfiguration(
            ex: InvalidTemplateConfigurationException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Invalid configuration",
                detail = "An error occurred because the notification level " +
                        "is not one of the following values ['none', 'priority', 'all']",
                status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(SubscribeToTopicException::class)
    fun handleSubscribeToTopic(
            ex: SubscribeToTopicException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Subscription failed",
                detail = "An error occurred when trying to subscribe to the board",
                status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(UnsubscribeFromTopicException::class)
    fun handleUnsubscribeFromTopic(
            ex: UnsubscribeFromTopicException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Unsubscription failed",
                detail = "An error occurred when trying to unsubscribe from the board",
                status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbidden(
            ex: ForbiddenException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Forbidden",
                detail = "You're not logged in or dont have authorization to perform this operation",
                status = HttpStatus.FORBIDDEN.value()
        )

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(AlreadyAMemberException::class)
    fun handleAlreadyAMember(
            ex: AlreadyAMemberException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "You're already subscribed",
                detail = "Already Subscribed",
                status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(NotAMemberException::class)
    fun handleNotAMember(
            ex: NotAMemberException,
            request: WebRequest
    ): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "You're not a member of the board",
                detail = "Only a member of the board can perform that action",
                status = HttpStatus.BAD_REQUEST.value()
        )

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<ProblemJson> {
        val error = ProblemJson(
                title = "Something went wrong",
                detail = ex.message,
                status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(error)
    }
}