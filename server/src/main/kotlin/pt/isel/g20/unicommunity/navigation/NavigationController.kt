package pt.isel.g20.unicommunity.navigation

import org.springframework.http.CacheControl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.board.model.MultipleBoardsResponseWithoutPagination
import pt.isel.g20.unicommunity.board.model.toItemRepr
import pt.isel.g20.unicommunity.common.APPLICATION_COLLECTION_JSON
import pt.isel.g20.unicommunity.common.APPLICATION_HAL_JSON
import pt.isel.g20.unicommunity.common.APPLICATION_JSON
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.presentation.AuthorizationOptional
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.navigation.model.HomeResponse
import pt.isel.g20.unicommunity.navigation.model.NavigationResponse
import pt.isel.g20.unicommunity.navigation.model.UnauthorizedNavigationResponse
import pt.isel.g20.unicommunity.navigation.service.INavigationService
import pt.isel.g20.unicommunity.user.model.User
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class NavigationController(private val service: INavigationService) {

    @GetMapping(path = [Uri.NAVIGATION_ROUTE], produces = [APPLICATION_HAL_JSON])
    @AuthorizationOptional
    fun getNavigation(@SessionAttribute("user") user: User?) =
        if(user == null)
            ResponseEntity
                    .ok()
                    .body(UnauthorizedNavigationResponse())
        else
            ResponseEntity
                    .ok()
                    .body(NavigationResponse(user.id))

    @GetMapping(path = [Uri.HOME_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getHome() =
            ResponseEntity
                    .ok()
                    .body(HomeResponse())

    @GetMapping(path = [Uri.MY_BOARDS], produces = [APPLICATION_COLLECTION_JSON])
    @AuthorizationOptional
    fun getMyBoards(
            @SessionAttribute("user") user: User?,
            @RequestParam(value = "page", required = false, defaultValue = "0") page: Int
    ) =
            service.getMyBoards(user!!.id).map(Board::toItemRepr).let {
                val response = CollectionObject(MultipleBoardsResponseWithoutPagination(it))

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }
}