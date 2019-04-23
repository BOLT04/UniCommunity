package pt.isel.g20.unicommunity.navigation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.g20.unicommunity.navigation.model.HomeResponse
import pt.isel.g20.unicommunity.navigation.model.NavigationResponse

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class NavigationController() {

    @GetMapping(path = ["/"], produces = ["application/hal+json"])
    fun getNavigation() =
        ResponseEntity
                .ok()
                .body(NavigationResponse())

    @GetMapping(path = ["/home"], produces = ["application/hal+json"])
    fun getHome() =
            ResponseEntity
                    .ok()
                    .body(HomeResponse())
}