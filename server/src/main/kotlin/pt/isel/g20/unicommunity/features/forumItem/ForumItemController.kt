package pt.isel.g20.unicommunity.features.forumItem

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.FORUMITEMS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_FORUMITEM_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.features.forumItem.model.ForumItemDto
import pt.isel.g20.unicommunity.features.forumItem.model.MultipleForumItemsResponse
import pt.isel.g20.unicommunity.features.forumItem.model.SingleForumItemResponse
import pt.isel.g20.unicommunity.features.forumItem.model.toItemRepr
import pt.isel.g20.unicommunity.features.forumItem.service.ForumItemService
import pt.isel.g20.unicommunity.features.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class ForumItemController(private val service: ForumItemService) {

    @AuthorizationRequired
    @GetMapping(path = [FORUMITEMS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllForumItems(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User
    )  =
            okResponse(
                    CollectionObject(
                            MultipleForumItemsResponse(
                                    boardId,
                                    service
                                            .getAllForumItems(boardId)
                                            .map { it.toItemRepr(user) }
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
            okResponse(
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
                createdResponse(responseBody, newResourceUri)
            }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_FORUMITEM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editForumItem(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @RequestBody forumItemDto: ForumItemDto,
            @SessionAttribute("user") user: User
    ) =
            okResponse(
                    SingleForumItemResponse(
                            user,
                            service.editForumItem(
                                    user,
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
            okResponse(SingleForumItemResponse(user, service.deleteForumItem(user, boardId, forumItemId)))
}