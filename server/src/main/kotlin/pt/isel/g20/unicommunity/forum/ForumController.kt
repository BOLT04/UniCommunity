package pt.isel.g20.unicommunity.forum

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forum.model.ForumDto
import pt.isel.g20.unicommunity.forum.model.SingleForumResponse
import pt.isel.g20.unicommunity.forum.service.IForumService
import pt.isel.g20.unicommunity.hateoas.Uri
import pt.isel.g20.unicommunity.hateoas.Uri.FORUM_ROUTE
import java.util.concurrent.TimeUnit

@RestController
class ForumController(private val service: IForumService) {

    @GetMapping(path = [FORUM_ROUTE])
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

    @PostMapping(path = [FORUM_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createForum(@PathVariable boardId: Long, @RequestBody forumDto: ForumDto) =
            service.createForum(boardId, forumDto.allowImagePosts).let {
                ResponseEntity
                        .created(Uri.forSingleForum(it.board!!.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumResponse(it))
            }

    @PutMapping(path = [FORUM_ROUTE])
    fun editForum(@PathVariable boardId: Long, @RequestBody forumDto: ForumDto) =
            service.editForum(boardId, forumDto.allowImagePosts).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumResponse(it))
            }


    @DeleteMapping(path = [FORUM_ROUTE])
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