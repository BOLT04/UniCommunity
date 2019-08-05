package pt.isel.g20.unicommunity.forum

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forum.model.SingleForumResponse
import pt.isel.g20.unicommunity.forum.service.IForumService
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.Uri.FORUM_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class ForumController(private val service: IForumService) {

    @AuthorizationRequired
    @GetMapping(path = [FORUM_ROUTE], produces = ["application/hal+json"])
    fun getForumById(@PathVariable boardId: Long) =
            service.getForumById(boardId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumResponse(it))
            }

    @AuthorizationRequired
    @PostMapping(path = [FORUM_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createForum(@PathVariable boardId: Long) =
            service.createForum(boardId).let {
                ResponseEntity
                        .created(Uri.forSingleForumUri(it.board.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumResponse(it))
            }

    @AuthorizationRequired
    @PutMapping(path = [FORUM_ROUTE], produces = ["application/hal+json"])
    fun editForum(@PathVariable boardId: Long) =
            service.editForum(boardId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumResponse(it))
            }

    @AuthorizationRequired
    @DeleteMapping(path = [FORUM_ROUTE], produces = ["application/hal+json"])
    fun deleteForum(@PathVariable boardId: Long) =
            service.deleteForum(boardId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumResponse(it))
            }


    @ExceptionHandler
    fun handleNotFoundForumException(e: NotFoundForumException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

    @ExceptionHandler
    fun handleNotFoundBoardException(e: NotFoundBoardException) =
            ResponseEntity
                    .notFound()
                    .build<String>()
}