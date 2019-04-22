package pt.isel.g20.unicommunity.navigation.model

import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link
import pt.isel.g20.unicommunity.hateoas.Rels
import pt.isel.g20.unicommunity.hateoas.Uri

class NavigationResponse()
    : HalObject(
        mutableMapOf(
                "self" to Link("/"),
                Rels.HOME to Link("/home"),
                Rels.LOGOUT to Link("/logout"),
                Rels.USER_PROFILE to Link("/profile"),
                Rels.MY_BOARDS to Link("/myBoards"),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards().toString()),
                Rels.CREATE_BOARD to Link("/createBoard")
        )
)

class HomeResponse()
    : HalObject(
        mutableMapOf(
                "self" to Link("/home"),
                Rels.GET_MULTIPLE_BOARDS to Link("/boards")
        )
)