package pt.isel.g20.unicommunity.features.usersBlackboards

import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.features.user.model.User
import pt.isel.g20.unicommunity.features.usersBlackboards.model.*
import pt.isel.g20.unicommunity.features.usersBlackboards.service.UsersBlackboardsService

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class UsersBlackboardsController(private val service: UsersBlackboardsService) {

    @AuthorizationRequired
    @GetMapping(path = [Uri.USERS_BLACKBOARDS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllUsersBlackboards(
            @PathVariable userId: Long,
            @SessionAttribute("user") user: User
    ) =
            okResponse(
                    CollectionObject(
                            MultipleUsersBlackboardsResponse(
                                    userId,
                                    service
                                            .getAllUsersBlackboards(user, userId)
                                            .map(UsersBlackboards::toItemRepr)
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [Uri.SINGLE_USERS_BLACKBOARD_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getUsersBlackboardById(
            @PathVariable userId: Long,
            @PathVariable bbId: Long,
            @SessionAttribute("user") user: User
    ) =
            okResponse(
                    SingleUsersBlackboardResponse(
                            service.getUsersBlackboardById(user, userId, bbId)
                    )
            )

    @AuthorizationRequired
    @PutMapping(path = [Uri.SINGLE_USERS_BLACKBOARD_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editUsersBlackboards(
            @PathVariable userId: Long,
            @PathVariable bbId: Long,
            @RequestBody usersBlackboardDto: UsersBlackboardsDto,
            @SessionAttribute("user") user: User
    ) =
            okResponse(
                    SingleUsersBlackboardResponse(
                            service.editUsersBlackboard(
                                    user,
                                    userId,
                                    bbId,
                                    usersBlackboardDto.notificationLevel
                            )
                    )
            )
}