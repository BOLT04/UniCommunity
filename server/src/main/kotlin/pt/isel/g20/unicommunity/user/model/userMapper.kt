package pt.isel.g20.unicommunity.user.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.CollectionLink
import pt.isel.g20.unicommunity.hateoas.Data
import pt.isel.g20.unicommunity.hateoas.Item

fun User.toItemRepr(sessionUser: User) : Item {
    val userId = this.id
    val isSessionUser = this.id == sessionUser.id

    val links = mutableListOf(
            CollectionLink(
                    rel = "self",
                    href = Uri.forSingleUserText(userId)
            )
    )

    if(isSessionUser)
        links.addAll(sequenceOf(
                CollectionLink(
                        rel = Rels.EDIT_USER,
                        href = Uri.forSingleUserText(userId)
                ),
                CollectionLink(
                        rel = Rels.DELETE_USER,
                        href = Uri.forSingleUserText(userId)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_USERS_BLACKBOARDS,
                        href = Uri.forAllUsersBlackboards(userId)
                ),
                CollectionLink(
                        rel = Rels.MY_BOARDS,
                        href = Uri.forMyBoards()
                )
        ))

    return Item(
            href = Uri.forSingleUserText(userId),
            data = listOf(
                    Data(name = "id", value = userId.toString()),
                    Data(name = "name", value = this.name),
                    Data(name = "email", value = this.email),
                    Data(name = "role", value = this.role),
                    Data(name = "githubId", value = this.githubId)
            ),
            links = links
    )
}