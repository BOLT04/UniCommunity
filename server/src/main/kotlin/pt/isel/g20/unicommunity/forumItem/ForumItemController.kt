package pt.isel.g20.unicommunity.forumItem

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.blackBlackboard.model.MultipleForumItemsResponse
import pt.isel.g20.unicommunity.blackBlackboard.model.SingleForumItemResponse
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.forumItem.model.ForumItemDto
import pt.isel.g20.unicommunity.forumItem.service.IForumItemService
import pt.isel.g20.unicommunity.hateoas.Uri
import pt.isel.g20.unicommunity.hateoas.Uri.FORUMITEMS_ROUTE
import pt.isel.g20.unicommunity.hateoas.Uri.SINGLE_FORUMITEM_ROUTE
import java.util.concurrent.TimeUnit

@RestController
class ForumItemController(private val service: IForumItemService) {

    @GetMapping(path = [FORUMITEMS_ROUTE])
    fun getAllForumItems(@PathVariable boardId: Long) =
            service.getAllForumItems(boardId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(MultipleForumItemsResponse(boardId, it))
            }

    @GetMapping(path = [SINGLE_FORUMITEM_ROUTE])
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
    fun createForumItem(@PathVariable boardId: Long, @RequestBody ForumItemDto: ForumItemDto) =
            service.createForumItem(boardId, ForumItemDto.name, ForumItemDto.content).let {
                ResponseEntity
                        .created(Uri.forSingleForumItem(it.forum!!.board!!.id, it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumItemResponse(it))
            }

    @PutMapping(path = [SINGLE_FORUMITEM_ROUTE])
    fun editForumItem(@PathVariable boardId: Long, @PathVariable forumItemId: Long, @RequestBody ForumItemDto: ForumItemDto) =
            service.editForumItem(boardId, forumItemId, ForumItemDto.name, ForumItemDto.content).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleForumItemResponse(it))
            }


    @DeleteMapping(path = [SINGLE_FORUMITEM_ROUTE])
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