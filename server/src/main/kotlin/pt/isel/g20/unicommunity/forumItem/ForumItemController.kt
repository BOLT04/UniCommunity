package pt.isel.g20.unicommunity.forumItem

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.forumItem.model.ForumItemDto
import pt.isel.g20.unicommunity.forumItem.model.MultipleForumItemsResponse
import pt.isel.g20.unicommunity.forumItem.model.SingleForumItemResponse
import pt.isel.g20.unicommunity.forumItem.service.IForumItemService
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.hateoas.Uri
import pt.isel.g20.unicommunity.hateoas.Uri.FORUMITEMS_ROUTE
import pt.isel.g20.unicommunity.hateoas.Uri.SINGLE_FORUMITEM_ROUTE
import pt.isel.g20.unicommunity.user.model.User
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class ForumItemController(private val service: IForumItemService) {

    @GetMapping(path = [FORUMITEMS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllForumItems(@PathVariable boardId: Long) : ResponseEntity<CollectionObject> =
            service.getAllForumItems(boardId).let {
                val rsp = CollectionObject(MultipleForumItemsResponse(boardId, it))

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(rsp.hashCode().toString())
                        .body(rsp)
            }

    @GetMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = ["application/hal+json"])
    fun getForumItemById(@PathVariable boardId: Long, @PathVariable forumItemId: Long) =
            service.getForumItemById(boardId, forumItemId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumItemResponse(it))
            }

    @PostMapping(path = [FORUMITEMS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createForumItem(
            @PathVariable boardId: Long,
            @RequestBody forumItemDto: ForumItemDto
    ): ResponseEntity<SingleForumItemResponse>{
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as User
            return service.createForumItem(boardId, user.id, forumItemDto.name, forumItemDto.content, forumItemDto.anonymousPost).let {
                ResponseEntity
                        .created(Uri.forSingleForumItem(it.forum!!.board!!.id, it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumItemResponse(it))
            }
    }

    @PutMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = ["application/hal+json"])
    fun editForumItem(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @RequestBody forumItemDto: ForumItemDto
    ) =
            service.editForumItem(boardId, forumItemId, forumItemDto.name, forumItemDto.content).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumItemResponse(it))
            }


    @DeleteMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = ["application/hal+json"])
    fun deleteForumItem(@PathVariable boardId: Long, @PathVariable forumItemId: Long) =
            service.deleteForumItem(boardId, forumItemId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumItemResponse(it))
            }


    @ExceptionHandler
    fun handleNotFoundForumItemException(e: NotFoundForumItemException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

    @ExceptionHandler
    fun handleNotFoundBoardException(e: NotFoundBoardException) =
            ResponseEntity
                    .notFound()
                    .build<String>()
}