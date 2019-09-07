package pt.isel.g20.unicommunity.features.auth.model

import pt.isel.g20.unicommunity.common.HalObject
import pt.isel.g20.unicommunity.common.Link
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.features.user.model.User

class LoginResponse(user: User)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.LOGIN_ROUTE),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.GET_USER_BLACKBOARDS_SETTINGS to Link(Uri.forAllUsersBlackboards(user.id))
        )
){
    val email: String = user.email
    val name : String = user.name
}