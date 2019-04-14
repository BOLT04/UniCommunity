package pt.isel.g20.unicommunity.user.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleUserResponse(user: User)
    : HalObject(
        mapOf(
                "self" to Link(Uri.forSingleUser(user.id).toString()),
                "states" to Link(Uri.forAllUsers().toString())
        )
){
    val name : String = user.name
    val email : String = user.email
    val githubId : String? = user.githubId
}


class MultipleUsersResponse(
        users : Iterable<User>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllUsers().toString(),
        links = listOf(
                CollectionLink(
                        rel = "/rels/createUsers",
                        href = "/users"
                )
        ),
        items = users.map { Item( Uri.forSingleUser(it.id).toString()) }
)