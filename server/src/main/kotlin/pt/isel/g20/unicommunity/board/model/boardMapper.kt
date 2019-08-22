package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.CollectionLink
import pt.isel.g20.unicommunity.hateoas.Data
import pt.isel.g20.unicommunity.hateoas.Item

fun Board.toItemRepr() = Item(
        href = Uri.forSingleBoardText(id),
        data =listOf(
                Data(name= "name", value= this.name),
                Data(name= "id", value= this.id.toString()),
                Data(name= "description", value= this.description)
        ),
        links = listOf(
                CollectionLink(
                        rel= "self",
                        href= Uri.forSingleBoardText(id)
                ),
                CollectionLink(
                        rel= Rels.EDIT_BOARD,
                        href= Uri.forSingleBoardText(this.id)
                ),
                CollectionLink(
                        rel= Rels.DELETE_BOARD,
                        href= Uri.forSingleBoardText(this.id)
                ),
                CollectionLink(
                        rel= Rels.GET_MULTIPLE_BOARDS,
                        href= Uri.forAllBoards()
                ),
                CollectionLink(
                        rel= Rels.SUBSCRIBE,
                        href= Uri.forBoardMembers(id)
                ),
                CollectionLink(
                        rel= Rels.UNSUBSCRIBE,
                        href= Uri.forBoardMembers(id)
                )
        )
)