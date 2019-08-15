package pt.isel.g20.unicommunity.comment

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.comment.exception.NotFoundCommentException
import pt.isel.g20.unicommunity.comment.model.*
import pt.isel.g20.unicommunity.comment.service.ICommentService
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.Uri.COMMENTS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_COMMENT_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException
import pt.isel.g20.unicommunity.user.model.User
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class CommentController(private val service: ICommentService) {

    @AuthorizationRequired
    @GetMapping(path = [COMMENTS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllComments(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long
    ) : ResponseEntity<CollectionObject> =
            service.getAllComments(boardId, forumItemId).map(Comment::toItemRepr).let {
                val rsp = CollectionObject(MultipleCommentsResponse(boardId, forumItemId, it))

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(rsp.hashCode().toString())
                        .body(rsp)
            }

    @AuthorizationRequired
    @GetMapping(path = [SINGLE_COMMENT_ROUTE], produces = ["application/hal+json"])
    fun getCommentById(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @PathVariable commentId: Long,
            @SessionAttribute("user") user: User
    ) =
            service.getCommentById(boardId, forumItemId, commentId).let {
                val response = SingleCommentResponse(user, it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @AuthorizationRequired
    @PostMapping(path = [COMMENTS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createComment(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @RequestBody commentDto: CommentDto,
            @SessionAttribute("user")user: User
    ):ResponseEntity<SingleCommentResponse>{
            return service.createComment(boardId, forumItemId, user.id, commentDto.content, commentDto.anonymous).let {
                val response = SingleCommentResponse(user, it)

                ResponseEntity
                        .created(Uri.forSingleCommentUri(it.forumItem.forum.board.id, it.forumItem.id, it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }
    }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_COMMENT_ROUTE], produces = ["application/hal+json"])
    fun editComment(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @PathVariable commentId: Long,
            @RequestBody commentDto: CommentDto,
            @SessionAttribute("user")user: User
    ) =
            service.editComment(boardId, forumItemId, commentId, commentDto.content).let {
                val response = SingleCommentResponse(user, it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @AuthorizationRequired
    @DeleteMapping(path = [SINGLE_COMMENT_ROUTE], produces = ["application/hal+json"])
    fun deleteComment(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @PathVariable commentId: Long,
            @SessionAttribute("user")user: User
    ) =
            service.deleteComment(boardId, forumItemId, commentId).let {
                val response = SingleCommentResponse(user, it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }


    @ExceptionHandler
    fun handleNotFoundCommentException(e: NotFoundCommentException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Comment was not found")

    @ExceptionHandler
    fun handleNotFoundBoardException(e: NotFoundBoardException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Board was not found")

    @ExceptionHandler
    fun handleNotFoundForumItemException(e: NotFoundForumItemException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Forum item was not found")

    @ExceptionHandler
    fun handleNotFoundForumException(e: NotFoundForumException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Forum was not found")

    @ExceptionHandler
    fun handleNotFoundUserException(e: NotFoundUserException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User was not found")
}