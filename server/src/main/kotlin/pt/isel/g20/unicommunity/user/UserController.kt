package pt.isel.g20.unicommunity.user

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.Uri.SINGLE_USER_ROUTE
import pt.isel.g20.unicommunity.common.Uri.USERS_ROUTE
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.user.exception.InvalidUserEmailException
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException
import pt.isel.g20.unicommunity.user.model.*
import pt.isel.g20.unicommunity.user.service.IUserService
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class UserController(private val service: IUserService) {

    @GetMapping(path = [USERS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllUsers() =
            service.getAllUsers().let {
                val rsp = CollectionObject(MultipleUsersResponse(it))

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(rsp.hashCode().toString())
                        .body(rsp)
            }

    @GetMapping(path = [SINGLE_USER_ROUTE], produces = ["application/hal+json"])
    fun getUserById(@PathVariable userId: Long) =
            service.getUserById(userId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleUserResponse(it))
            }

    @PostMapping(path = [USERS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userDto: UserDto) =
            service.createUser(userDto.name, userDto.email, userDto.password, userDto.githubId).let {
                ResponseEntity
                        .created(Uri.forSingleUserUri(it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleUserResponse(it))
            }

    @PutMapping(path = [SINGLE_USER_ROUTE], produces = ["application/hal+json"])
    fun editUser(@PathVariable userId: Long, @RequestBody userDto: UserDto) =
            service.editUser(userId, userDto.name, userDto.email, userDto.password, userDto.githubId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleUserResponse(it))
            }


    @DeleteMapping(path = [SINGLE_USER_ROUTE], produces = ["application/hal+json"])
    fun deleteUser(@PathVariable userId: Long) =
            service.deleteUser(userId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleUserResponse(it))
            }

    @ExceptionHandler
    fun handleInvalidUserEmailException(e: InvalidUserEmailException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

    @ExceptionHandler
    fun handleNotFoundUserException(e: NotFoundUserException) =
            ResponseEntity
                    .notFound()
                    .build<String>()
}