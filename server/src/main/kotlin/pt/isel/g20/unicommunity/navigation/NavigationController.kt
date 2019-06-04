package pt.isel.g20.unicommunity.navigation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.SessionAttribute
import pt.isel.g20.unicommunity.common.APPLICATION_COLLECTION_JSON
import pt.isel.g20.unicommunity.common.APPLICATION_HAL_JSON
import pt.isel.g20.unicommunity.common.APPLICATION_JSON
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.presentation.AuthorizationOptional
import pt.isel.g20.unicommunity.navigation.model.HomeResponse
import pt.isel.g20.unicommunity.navigation.model.NavigationResponse
import pt.isel.g20.unicommunity.navigation.model.UnauthorizedNavigationResponse
import pt.isel.g20.unicommunity.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class NavigationController {


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
}