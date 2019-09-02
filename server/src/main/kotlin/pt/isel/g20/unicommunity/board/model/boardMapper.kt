package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.CollectionLink
import pt.isel.g20.unicommunity.hateoas.Data
import pt.isel.g20.unicommunity.hateoas.Item
import pt.isel.g20.unicommunity.user.model.User

fun Board.toItemRepr(user: User): Item {

    val links =
            mutableListOf(
                CollectionLink(
                        rel= "self",
                        href= Uri.forSingleBoardText(id)
                ),
                CollectionLink(
                        rel= Rels.GET_MULTIPLE_BOARDS,
                        href= Uri.forAllBoards()
                )
            )

    if(this.getMembers().find { it.id == user.id } != null)
        links.add(
                CollectionLink(
                    rel= Rels.UNSUBSCRIBE,
                    href= Uri.forBoardMembers(id)
                )
        )
    else
        links.add(
                CollectionLink(
                        rel= Rels.SUBSCRIBE,
                        href= Uri.forBoardMembers(id)
                )
        )

    if(this.creator.id == user.id)
       links.addAll(sequenceOf(
                CollectionLink(
                        rel= Rels.EDIT_BOARD,
                        href= Uri.forSingleBoardText(this.id)
                ),
                CollectionLink(
                        rel= Rels.DELETE_BOARD,
                        href= Uri.forSingleBoardText(this.id)
                )
        ))

    return Item(
            href = Uri.forSingleBoardText(id),
            data = mutableListOf(
                    Data(name= "name", value= this.name),
                    Data(name= "id", value= this.id.toString()),
                    Data(name= "description", value= this.description)
            ),
            links = links
    )
}