package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

fun Blackboard.toItemRepr() = Item(
        href = Uri.forSingleBlackboardText(this.board.id, this.id),
        data = listOf(
                Data(name = "name", value = this.name),
                Data(name = "id", value = this.id.toString()),
                Data(name = "description", value = this.description),
                Data(name = "notificationLevel", value = this.notificationLevel)
        ),
        links = listOf(
                CollectionLink(
                        rel = "self",
                        href = Uri.forSingleBlackboardText(this.board.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.EDIT_BLACKBOARD,
                        href = Uri.forSingleBlackboardText(this.board.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.DELETE_BLACKBOARD,
                        href = Uri.forSingleBlackboardText(this.board.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BLACKBOARDS,
                        href = Uri.forAllBlackboards(this.board.id)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BOARDS,
                        href = Uri.forAllBoards()
                )
        )
)