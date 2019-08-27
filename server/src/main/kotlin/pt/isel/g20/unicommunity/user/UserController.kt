package pt.isel.g20.unicommunity.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.BOARD_MEMBERS
import pt.isel.g20.unicommunity.common.Uri.SINGLE_USER_ROUTE
import pt.isel.g20.unicommunity.common.Uri.USERS_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.user.model.MultipleUsersResponse
import pt.isel.g20.unicommunity.user.model.SingleUserResponse
import pt.isel.g20.unicommunity.user.model.User
import pt.isel.g20.unicommunity.user.model.UserDto
import pt.isel.g20.unicommunity.user.service.IUserService

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class UserController(private val service: IUserService) {

    @GetMapping(path = [USERS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllUsers() =
            cacheOkResponse(CollectionObject(MultipleUsersResponse(service.getAllUsers())))

    @GetMapping(path = [SINGLE_USER_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getUserById(@PathVariable userId: Long) =
            cacheOkResponse(SingleUserResponse(service.getUserById(userId)))

    @AuthorizationRequired
    @PostMapping(path = [USERS_ROUTE], produces = [APPLICATION_HAL_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userDto: UserDto, @SessionAttribute("user")user: User) =
            service.createUser(
                    user.id,
                    userDto.name,
                    userDto.email,
                    userDto.password,
                    userDto.role,
                    userDto.githubId
            ).let {
                val responseBody = SingleUserResponse(it)
                val newResourceHref = Uri.forSingleUserUri(it.id)
                cacheCreatedResponse(responseBody, newResourceHref)
            }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_USER_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editUser(@PathVariable userId: Long, @RequestBody userDto: UserDto) =
            cacheOkResponse(
                    SingleUserResponse(
                            service.editUser(
                                    userId,
                                    userDto.name,
                                    userDto.email,
                                    userDto.password,
                                    userDto.githubId
                            )
                    )
            )

    @AuthorizationRequired
    @DeleteMapping(path = [SINGLE_USER_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun deleteUser(@PathVariable userId: Long) =
            cacheOkResponse(SingleUserResponse(service.deleteUser(userId)))


    @GetMapping(path = [BOARD_MEMBERS], produces = [APPLICATION_COLLECTION_JSON])
    fun getBoardMembers(@PathVariable boardId: Long) =
            cacheOkResponse(CollectionObject(MultipleUsersResponse(service.getBoardMembers(boardId))))
}