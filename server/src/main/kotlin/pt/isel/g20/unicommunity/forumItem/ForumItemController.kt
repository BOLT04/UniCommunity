package pt.isel.g20.unicommunity.forumItem

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.forumItem.model.*
import pt.isel.g20.unicommunity.forumItem.service.IForumItemService
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.Uri.FORUMITEMS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_FORUMITEM_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.user.model.User
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class ForumItemController(private val service: IForumItemService) {

    @AuthorizationRequired
    @GetMapping(path = [FORUMITEMS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllForumItems(@PathVariable boardId: Long) : ResponseEntity<CollectionObject> =
            service.getAllForumItems(boardId).map(ForumItem::toItemRepr).let {
                val response = CollectionObject(MultipleForumItemsResponse(boardId, it))

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
    @GetMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = ["application/hal+json"])
    fun getForumItemById(@PathVariable boardId: Long,
                         @PathVariable forumItemId: Long,
                         @SessionAttribute("user") user: User
    ) =
            service.getForumItemById(boardId, forumItemId).let {
                val response = SingleForumItemResponse(user, it)

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
    @PostMapping(path = [FORUMITEMS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createForumItem(
            @PathVariable boardId: Long,
            @RequestBody forumItemDto: ForumItemDto,
            @SessionAttribute("user")user: User
    ): ResponseEntity<SingleForumItemResponse>{
            return service.createForumItem(
                    boardId,
                    user.id,
                    forumItemDto.name,
                    forumItemDto.content,
                    forumItemDto.anonymousPost
            ).let {
                val response = SingleForumItemResponse(user, it)

                ResponseEntity
                        .created(Uri.forSingleForumItemUri(it.forum.board.id, it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }
    }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = ["application/hal+json"])
    fun editForumItem(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @RequestBody forumItemDto: ForumItemDto,
            @SessionAttribute("user") user: User
    ) =
            service.editForumItem(boardId, forumItemId, forumItemDto.name, forumItemDto.content).let {
                val response = SingleForumItemResponse(user, it)

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
    @DeleteMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = ["application/hal+json"])
    fun deleteForumItem(@PathVariable boardId: Long,
                        @PathVariable forumItemId: Long,
                        @SessionAttribute("user") user: User
    ) =
            service.deleteForumItem(boardId, forumItemId).let {
                val response = SingleForumItemResponse(user, it)

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