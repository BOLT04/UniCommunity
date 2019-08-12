package pt.isel.g20.unicommunity.navigation.model

import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.SingleHalObj

class NavigationResponse(userId: Long)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.NAVIGATION_ROUTE),
                Rels.HOME to Link(Uri.HOME_ROUTE),
                Rels.LOGOUT to Link("/logout"),
                Rels.USER_PROFILE to Link(Uri.forSingleUserText(userId)),
                Rels.MY_BOARDS to Link("/myBoards"),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),
                Rels.CREATE_BOARD to Link(Uri.forAllBoards())
        )
)

class HomeResponse : HalObject(
    _links=  mutableMapOf(
        "self" to Link(Uri.HOME_ROUTE),
        Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE)
    ),
    _embedded = mutableMapOf(
        Rels.LOGIN to SingleHalObj(
            HalObject(_links= mutableMapOf("self" to Link(Uri.LOGIN_ROUTE)))),
        Rels.GET_MULTIPLE_BOARDS to SingleHalObj(
            HalObject(_links= mutableMapOf("self" to Link(Uri.BOARDS_ROUTE))))
    )
)

class UnauthorizedNavigationResponse
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.NAVIGATION_ROUTE),
                //Rels.HOME to Link(Uri.HOME_ROUTE),
                Rels.LOGIN to Link(Uri.LOGIN_ROUTE)
                //Rels.LOGOUT to Link("/logout"), //TODO: for now since we don't have cookie auth, we don't need logout
        )
)