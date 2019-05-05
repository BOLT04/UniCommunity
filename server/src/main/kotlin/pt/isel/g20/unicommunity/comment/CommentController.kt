package pt.isel.g20.unicommunity.comment

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.comment.exception.NotFoundCommentException
import pt.isel.g20.unicommunity.comment.model.CommentDto
import pt.isel.g20.unicommunity.comment.model.MultipleCommentsResponse
import pt.isel.g20.unicommunity.comment.model.SingleCommentResponse
import pt.isel.g20.unicommunity.comment.service.ICommentService
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.hateoas.Uri
import pt.isel.g20.unicommunity.hateoas.Uri.COMMENTS_ROUTE
import pt.isel.g20.unicommunity.hateoas.Uri.SINGLE_COMMENT_ROUTE
import pt.isel.g20.unicommunity.user.model.User
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class CommentController(private val service: ICommentService) {

    @GetMapping(path = [COMMENTS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllComments(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long
    ) : ResponseEntity<CollectionObject> =
            service.getAllComments(boardId, forumItemId).let {
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

    @GetMapping(path = [SINGLE_COMMENT_ROUTE], produces = ["application/hal+json"])
    fun getCommentById(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @PathVariable commentId: Long
    ) =
            service.getCommentById(boardId, forumItemId, commentId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleCommentResponse(it))
            }

    @PostMapping(path = [COMMENTS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createComment(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @RequestBody commentDto: CommentDto
    ):ResponseEntity<SingleCommentResponse>{
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as User
            return service.createComment(boardId, forumItemId, user.id, commentDto.content, commentDto.anonymous).let {
                ResponseEntity
                        .created(Uri.forSingleComment(it.forumItem!!.forum!!.board!!.id, it.forumItem!!.id, it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleCommentResponse(it))
            }
    }

    @PutMapping(path = [SINGLE_COMMENT_ROUTE], produces = ["application/hal+json"])
    fun editComment(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @PathVariable commentId: Long,
            @RequestBody commentDto: CommentDto
    ) =
            service.editComment(boardId, forumItemId, commentId, commentDto.content).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleCommentResponse(it))
            }


    @DeleteMapping(path = [SINGLE_COMMENT_ROUTE], produces = ["application/hal+json"])
    fun deleteComment(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @PathVariable commentId: Long
    ) =
            service.deleteComment(boardId, forumItemId, commentId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleCommentResponse(it))
            }


    @ExceptionHandler
    fun handleNotFoundCommentException(e: NotFoundCommentException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

    @ExceptionHandler
    fun handleNotFoundBoardException(e: NotFoundBoardException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

    @ExceptionHandler
    fun handleNotFoundForumItemException(e: NotFoundForumItemException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

    @ExceptionHandler
    fun handleNotFoundForumException(e: NotFoundForumException) =
            ResponseEntity
                    .notFound()
                    .build<String>()
}