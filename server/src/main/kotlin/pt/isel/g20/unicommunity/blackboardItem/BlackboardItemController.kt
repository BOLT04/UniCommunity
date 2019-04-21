package pt.isel.g20.unicommunity.BlackboardItem

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboardItem.exception.NotFoundBlackboardItemException
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItemDto
import pt.isel.g20.unicommunity.blackboardItem.model.MultipleBlackboardItemsResponse
import pt.isel.g20.unicommunity.blackboardItem.model.SingleBlackboardItemResponse
import pt.isel.g20.unicommunity.blackboardItem.service.IBlackboardItemService
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.hateoas.Uri
import pt.isel.g20.unicommunity.hateoas.Uri.BLACKBOARDITEMS_ROUTE
import pt.isel.g20.unicommunity.hateoas.Uri.SINGLE_BLACKBOARDITEM_ROUTE
import java.util.concurrent.TimeUnit

@RestController
class BlackboardItemController(private val service: IBlackboardItemService) {

    @GetMapping(path = [BLACKBOARDITEMS_ROUTE])
    fun getAllBlackboardItems(@PathVariable boardId: Long, @PathVariable bbId: Long) =
            service.getAllBlackboardItems(boardId, bbId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(MultipleBlackboardItemsResponse(boardId, bbId, it))
            }

    @GetMapping(path = [SINGLE_BLACKBOARDITEM_ROUTE])
    fun getBlackboardItemById(@PathVariable boardId: Long, @PathVariable bbId: Long, @PathVariable itemId: Long) =
            service.getBlackboardItemById(boardId, bbId, itemId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBlackboardItemResponse(it))
            }

    @PostMapping(path = [BLACKBOARDITEMS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBlackboardItem(@PathVariable boardId: Long, @PathVariable bbId: Long, @RequestBody itemDto: BlackboardItemDto) =
            service.createBlackboardItem(boardId, bbId, itemDto.name, itemDto.content).let {
                ResponseEntity
                        .created(Uri.forSingleBlackboardItem(it.boardId, it.bbId, it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBlackboardItemResponse(it))
            }

    @PutMapping(path = [SINGLE_BLACKBOARDITEM_ROUTE])
    fun editBlackboardItem(@PathVariable boardId: Long, @PathVariable bbId: Long, @PathVariable itemId: Long, @RequestBody itemDto: BlackboardItemDto) =
            service.editBlackboardItem(boardId, bbId, itemId, itemDto.name, itemDto.content).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBlackboardItemResponse(it))
            }


    @DeleteMapping(path = [SINGLE_BLACKBOARDITEM_ROUTE])
    fun deleteBlackboardItem(@PathVariable boardId: Long, @PathVariable bbId: Long, @PathVariable itemId: Long) =
            service.deleteBlackboardItem(boardId, bbId, itemId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBlackboardItemResponse(it))
            }


    @ExceptionHandler
    fun handleNotFoundBlackboardItemException(e: NotFoundBlackboardItemException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

    @ExceptionHandler
    fun handleNotFoundBlackboardException(e: NotFoundBlackboardException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

    @ExceptionHandler
    fun handleNotFoundBoardException(e: NotFoundBoardException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

}