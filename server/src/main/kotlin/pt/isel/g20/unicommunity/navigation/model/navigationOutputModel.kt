package pt.isel.g20.unicommunity.navigation.model

import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri

class NavigationResponse(userId: Long)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.NAVIGATION_ROUTE),
                Rels.HOME to Link(Uri.HOME_ROUTE),
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
                "self" to Link(Uri.HOME_ROUTE),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardTemplated()),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE)
        )
)

class UnauthorizedNavigationResponse()
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.NAVIGATION_ROUTE),
                //Rels.HOME to Link(Uri.HOME_ROUTE),
                Rels.LOGIN to Link(Uri.LOGIN_ROUTE+"a")
                //Rels.LOGOUT to Link("/logout"), //TODO: for now since we don't have cookie auth, we don't need logout
        )
)