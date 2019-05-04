package pt.isel.g20.unicommunity.user.model

import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.hateoas.*

class SingleUserResponse(user: User)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleUser(user.id).toString()),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.CREATE_USER to Link(Uri.forAllUsers().toString()),
                Rels.GET_MULTIPLE_USERS to Link(Uri.forAllUsers().toString()),
                Rels.EDIT_USER to Link(Uri.forSingleUser(user.id).toString()),
                Rels.DELETE_USER to Link(Uri.forSingleUser(user.id).toString())
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
                                    "self" to Link(Uri.forSingleBoard(it.id).toString())
                            ))
                }
        ))
    }
}


class MultipleUsersResponse(
        users : Iterable<User>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllUsers().toString(),
        links = listOf(
                CollectionLink("self","/http://localhost:3000/users"),
                CollectionLink(Rels.NAVIGATION, "/http://localhost:3000/navigation")
        ),
        items = users.map { Item( Uri.forSingleUser(it.id).toString()) }
)

class PartialUserObject(
        val name: String,
        val _links: Map<String, Link>
) : HalResourceObject()