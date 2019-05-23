package pt.isel.g20.unicommunity.navigation.model

import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri

class NavigationResponse
    : HalObject(
        mutableMapOf(
                "self" to Link("/"),
                Rels.HOME to Link("/home"),
                Rels.LOGOUT to Link("/logout"),
                Rels.USER_PROFILE to Link("/profile"),
                Rels.MY_BOARDS to Link("/myBoards"),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),
                Rels.CREATE_BOARD to Link(Uri.forAllBoards())
        )
)

class HomeResponse
    : HalObject(
        mutableMapOf(
                "self" to Link("/home"),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardTemplated()),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE)
        )
)