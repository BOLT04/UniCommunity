package pt.isel.g20.unicommunity.forumItem

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.FORUMITEMS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_FORUMITEM_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.forumItem.model.ForumItemDto
import pt.isel.g20.unicommunity.forumItem.model.MultipleForumItemsResponse
import pt.isel.g20.unicommunity.forumItem.model.SingleForumItemResponse
import pt.isel.g20.unicommunity.forumItem.model.toItemRepr
import pt.isel.g20.unicommunity.forumItem.service.IForumItemService
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class ForumItemController(private val service: IForumItemService) {

    @AuthorizationRequired
    @GetMapping(path = [FORUMITEMS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllForumItems(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User
    )  =
            cacheOkResponse(
                    CollectionObject(
                            MultipleForumItemsResponse(
                                    boardId,
                                    service
                                            .getAllForumItems(boardId)
                                            .map{it.toItemRepr(user)}
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getForumItemById(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    SingleForumItemResponse(
                            user,
                            service.getForumItemById(boardId, forumItemId)
                    )
            )

    @AuthorizationRequired
    @PostMapping(path = [FORUMITEMS_ROUTE], produces = [APPLICATION_HAL_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createForumItem(
            @PathVariable boardId: Long,
            @RequestBody forumItemDto: ForumItemDto,
            @SessionAttribute("user")user: User
    ) =
            service.createForumItem(
                    boardId,
                    user.id,
                    forumItemDto.name,
                    forumItemDto.content,
                    forumItemDto.anonymousPost
            ).let {
                val responseBody = SingleForumItemResponse(user, it)
                val newResourceUri = Uri.forSingleForumItemUri(it.forum.board.id, it.id)
                cacheCreatedResponse(responseBody, newResourceUri)
            }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editForumItem(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @RequestBody forumItemDto: ForumItemDto,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    SingleForumItemResponse(
                            user,
                            service.editForumItem(
                                    boardId,
                                    forumItemId,
                                    forumItemDto.name,
                                    forumItemDto.content
                            )
                    )
            )

    @AuthorizationRequired
    @DeleteMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun deleteForumItem(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(SingleForumItemResponse(user, service.deleteForumItem(boardId, forumItemId)))
}