package pt.isel.g20.unicommunity.blackboardItem

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.blackboardItem.model.*
import pt.isel.g20.unicommunity.blackboardItem.service.IBlackboardItemService
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.BLACKBOARDITEMS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_BLACKBOARDITEM_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class BlackboardItemController(private val service: IBlackboardItemService) {

    @AuthorizationRequired
    @GetMapping(path = [BLACKBOARDITEMS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllBlackboardItems(@PathVariable boardId: Long, @PathVariable bbId: Long) =
            cacheOkResponse(
                    CollectionObject(
                            MultipleBlackboardItemsResponse(
                                    boardId,
                                    bbId,
                                    service
                                            .getAllBlackboardItems(boardId, bbId)
                                            .map(BlackboardItem::toItemRepr)
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [SINGLE_BLACKBOARDITEM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getBlackboardItemById(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @PathVariable itemId: Long,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    SingleBlackboardItemResponse(
                            user,
                            service.getBlackboardItemById(boardId, bbId, itemId)
                    )
            )

    @AuthorizationRequired
    @PostMapping(path = [BLACKBOARDITEMS_ROUTE], produces = [APPLICATION_HAL_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBlackboardItem(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @RequestBody itemDto: BlackboardItemDto,
            @SessionAttribute("user")user: User
    ) =
            service.createBlackboardItem(
                    boardId,
                    bbId,
                    user.id,
                    itemDto.name,
                    itemDto.content
            ).let {
                val responseBody = SingleBlackboardItemResponse(user, it)
                val newResourceHref = Uri.forSingleBlackboardItemUri(
                        it.blackboard.board.id,
                        it.blackboard.id,
                        it.id
                )
                cacheCreatedResponse(responseBody, newResourceHref)
            }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_BLACKBOARDITEM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editBlackboardItem(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @PathVariable itemId: Long,
            @RequestBody itemDto: BlackboardItemDto,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    SingleBlackboardItemResponse(
                            user,
                            service.editBlackboardItem(
                                    boardId,
                                    bbId,
                                    itemId,
                                    itemDto.name,
                                    itemDto.content
                            )
                    )
            )

    @AuthorizationRequired
    @DeleteMapping(path = [SINGLE_BLACKBOARDITEM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun deleteBlackboardItem(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @PathVariable itemId: Long,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    SingleBlackboardItemResponse(
                            user,
                            service.deleteBlackboardItem(boardId, bbId, itemId)
                    )
            )
}