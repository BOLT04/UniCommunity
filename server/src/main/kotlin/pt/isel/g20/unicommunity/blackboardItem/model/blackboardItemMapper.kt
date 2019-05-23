package pt.isel.g20.unicommunity.blackboardItem.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

fun BlackboardItem.toItemRepr() = Item(
        href = Uri.forSingleBlackboardItemText(this.blackboard.board.id, this.blackboard.id, this.id),
        data = listOf(
                Data(name = "name", value = this.name),
                Data(name = "id", value = this.id.toString()),
                Data(name = "content", value = this.content),
                Data(name = "authorName", value = this.author.name),
                Data(name = "createdAt", value = this.createdAt.toString())
        ),
        links = listOf(
                CollectionLink(
                        rel = "self",
                        href = Uri.forSingleBlackboardItemText(this.blackboard.board.id, this.blackboard.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.EDIT_BLACKBOARD,
                        href = Uri.forSingleBlackboardItemText(this.blackboard.board.id, this.blackboard.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.DELETE_BLACKBOARD,
                        href = Uri.forSingleBlackboardItemText(this.blackboard.board.id, this.blackboard.id, this.id)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BLACKBOARDITEMS,
                        href = Uri.forAllBlackboardItems(this.blackboard.board.id, this.blackboard.id)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BLACKBOARDS,
                        href = Uri.forAllBlackboards(this.blackboard.board.id)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BOARDS,
                        href = Uri.forAllBoards()
                )
        )
)
