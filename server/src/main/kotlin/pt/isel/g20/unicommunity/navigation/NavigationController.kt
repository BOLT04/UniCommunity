package pt.isel.g20.unicommunity.navigation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.g20.unicommunity.navigation.model.HomeResponse
import pt.isel.g20.unicommunity.navigation.model.NavigationResponse

@RestController
class NavigationController() {

    @GetMapping(path = ["/"])
    fun getNavigation() =
        ResponseEntity
                .ok()
                .body(NavigationResponse())


    @GetMapping(path = ["/home"])
    fun getHome() =
            ResponseEntity
                    .ok()
                    .body(HomeResponse())
}