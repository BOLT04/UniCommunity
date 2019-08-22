package pt.isel.g20.unicommunity.blackboard

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.blackboard.model.*
import pt.isel.g20.unicommunity.blackboard.service.IBlackboardService
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.BLACKBOARDS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_BLACKBOARD_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class BlackboardController(private val service: IBlackboardService) {
    @AuthorizationRequired
    @GetMapping(path = [BLACKBOARDS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllBlackboards(@PathVariable boardId: Long) =
            cacheOkResponse(
                    CollectionObject(
                            MultipleBlackboardsResponse(
                                    boardId,
                                    service
                                            .getAllBlackboards(boardId)
                                            .map(Blackboard::toItemRepr)
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [SINGLE_BLACKBOARD_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getBlackboardById(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    SingleBlackboardResponse(
                            user,
                            service.getBlackboardById(boardId, bbId)
                    )
            )

    @AuthorizationRequired
    @PostMapping(path = [BLACKBOARDS_ROUTE], produces = [APPLICATION_HAL_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBlackboard(
            @PathVariable boardId: Long,
            @RequestBody blackboardDto: BlackboardDto,
            @SessionAttribute("user") user: User
    ) =
            service.createBlackboard(
                    user.id,
                    boardId,
                    blackboardDto.name,
                    blackboardDto.notificationLevel,
                    blackboardDto.description
            ).let {
                val responseBody = SingleBlackboardResponse(user, it)
                val newResourceHref = Uri.forSingleBlackboardUri(it.board.id, it.id)
                cacheCreatedResponse(responseBody, newResourceHref)
            }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_BLACKBOARD_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editBlackboard(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @RequestBody blackboardDto: BlackboardDto,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    SingleBlackboardResponse(
                            user,
                            service.editBlackboard(
                                    boardId,
                                    bbId,
                                    blackboardDto.name,
                                    blackboardDto.notificationLevel,
                                    blackboardDto.description
                            )
                    )
            )

    @AuthorizationRequired
    @DeleteMapping(path = [SINGLE_BLACKBOARD_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun deleteBlackboard(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    SingleBlackboardResponse(
                            user,
                            service.deleteBlackboard(boardId, bbId)
                    )
            )
}