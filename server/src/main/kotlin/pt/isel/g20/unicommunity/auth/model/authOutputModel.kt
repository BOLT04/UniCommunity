package pt.isel.g20.unicommunity.auth.model

import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.user.model.User

class LoginResponse(user: User)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.LOGIN_ROUTE),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards())
                //Rels.LOGOUT to Link("/logout"), //TODO: for now since we don't have cookie auth, we don't need logout

        )
){
    val email: String = user.email
    val name : String = user.name
}