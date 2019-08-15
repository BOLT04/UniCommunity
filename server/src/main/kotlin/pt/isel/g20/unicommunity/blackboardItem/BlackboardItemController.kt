package pt.isel.g20.unicommunity.blackboardItem

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboardItem.exception.NotFoundBlackboardItemException
import pt.isel.g20.unicommunity.blackboardItem.model.*
import pt.isel.g20.unicommunity.blackboardItem.service.IBlackboardItemService
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.Uri.BLACKBOARDITEMS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_BLACKBOARDITEM_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException
import pt.isel.g20.unicommunity.user.model.User
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class BlackboardItemController(private val service: IBlackboardItemService) {

    @AuthorizationRequired
    @GetMapping(path = [BLACKBOARDITEMS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllBlackboardItems(@PathVariable boardId: Long, @PathVariable bbId: Long) =
            service.getAllBlackboardItems(boardId, bbId).map(BlackboardItem::toItemRepr).let {
                val response = CollectionObject(MultipleBlackboardItemsResponse(boardId, bbId, it))

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
    @GetMapping(path = [SINGLE_BLACKBOARDITEM_ROUTE], produces = ["application/hal+json"])
    fun getBlackboardItemById(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @PathVariable itemId: Long,
            @SessionAttribute("user") user: User
    ) =
            service.getBlackboardItemById(boardId, bbId, itemId).let {
                val response = SingleBlackboardItemResponse(user, it)

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
    @PostMapping(path = [BLACKBOARDITEMS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBlackboardItem(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @RequestBody itemDto: BlackboardItemDto,
            @SessionAttribute("user")user: User
    ): ResponseEntity<SingleBlackboardItemResponse>{
            return service.createBlackboardItem(boardId, bbId, user.id, itemDto.name, itemDto.content).let {
                val response = SingleBlackboardItemResponse(user, it)

                ResponseEntity
                        .created(Uri.forSingleBlackboardItemUri(it.blackboard.board.id, it.blackboard.id, it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }
    }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_BLACKBOARDITEM_ROUTE], produces = ["application/hal+json"])
    fun editBlackboardItem(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @PathVariable itemId: Long,
            @RequestBody itemDto: BlackboardItemDto,
            @SessionAttribute("user") user: User
    ) =
            service.editBlackboardItem(boardId, bbId, itemId, itemDto.name, itemDto.content).let {
                val response = SingleBlackboardItemResponse(user, it)

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
    @DeleteMapping(path = [SINGLE_BLACKBOARDITEM_ROUTE], produces = ["application/hal+json"])
    fun deleteBlackboardItem(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @PathVariable itemId: Long,
            @SessionAttribute("user") user: User
    ) =
            service.deleteBlackboardItem(boardId, bbId, itemId).let {
                val response = SingleBlackboardItemResponse(user, it)

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
    fun handleNotFoundBlackboardItemException(e: NotFoundBlackboardItemException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Blackboard item was not found")

    @ExceptionHandler
    fun handleNotFoundBlackboardException(e: NotFoundBlackboardException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Blackboard was not found")

    @ExceptionHandler
    fun handleNotFoundBoardException(e: NotFoundBoardException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Board was not found")

    @ExceptionHandler
    fun handleNotFoundUserException(e: NotFoundUserException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User was not found")

}