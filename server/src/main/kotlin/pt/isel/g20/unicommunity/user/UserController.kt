package pt.isel.g20.unicommunity.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import pt.isel.g20.unicommunity.user.service.IUserService
import pt.isel.g20.unicommunity.user.model.UserDto
import pt.isel.g20.unicommunity.user.model.UserLinksResponse
import pt.isel.g20.unicommunity.user.model.UserResponse

private const val LIST_USERS_ROUTE = "/api/users"
private const val GET_USER_ROUTE = "/api/users/{userId}"

@RestController
class UserController(private val service: IUserService) {

    @GetMapping(path = [LIST_USERS_ROUTE])
    fun getAllUsers() = service.getAllUsers()

    @PostMapping(path = [LIST_USERS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userDto: UserDto): UserLinksResponse {
        service.createUser(userDto.toModel())

        return UserLinksResponse(
                "$LIST_USERS_ROUTE/${userDto.id}"
        )
    }

    @RequestMapping(GET_USER_ROUTE, method = [RequestMethod.GET])
    fun getUserById(@PathVariable(value="userId") name: String) : UserResponse {
        val user = service.getUserById(name)
                ?: throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A user with the given ID couldn't be found.")

        return UserResponse(user.id, user.name, user.email,
                "$LIST_USERS_ROUTE/${user.id}"
        )
    }
}