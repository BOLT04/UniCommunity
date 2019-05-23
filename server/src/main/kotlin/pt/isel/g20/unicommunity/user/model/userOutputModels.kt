package pt.isel.g20.unicommunity.user.model

import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

class SingleUserResponse(user: User)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleUserText(user.id)),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.CREATE_USER to Link(Uri.forAllUsers()),
                Rels.GET_MULTIPLE_USERS to Link(Uri.forAllUsers()),
                Rels.EDIT_USER to Link(Uri.forSingleUserText(user.id)),
                Rels.DELETE_USER to Link(Uri.forSingleUserText(user.id))
        ),
        mutableMapOf()
){
    val name : String = user.name
    val email : String = user.email
    val githubId : String? = user.githubId

    init {
        if(user.boards.size != 0)
        super._embedded?.putAll(sequenceOf(
                Rels.GET_MULTIPLE_BOARDS to user.boards.map {
                    PartialBoardObject(it.name,
                            mapOf(
                                    "self" to Link(Uri.forSingleBoardText(it.id))
                            ))
                }
        ))
    }
}


class MultipleUsersResponse(
        users : Iterable<User>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllUsers(),
        links = listOf(
                CollectionLink("self",Uri.forAllUsers()),
                CollectionLink(Rels.NAVIGATION, Uri.NAVIGATION_ROUTE)
        ),
        items = users.map { Item( Uri.forSingleUserText(it.id)) }
)

class PartialUserObject(
        val name: String,
        val _links: Map<String, Link>
) : HalResourceObject()