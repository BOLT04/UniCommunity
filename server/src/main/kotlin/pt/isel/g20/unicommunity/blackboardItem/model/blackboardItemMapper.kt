package pt.isel.g20.unicommunity.blackboardItem.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.CollectionLink
import pt.isel.g20.unicommunity.hateoas.Data
import pt.isel.g20.unicommunity.hateoas.Item
import pt.isel.g20.unicommunity.user.model.User

fun BlackboardItem.toItemRepr(user: User) : Item {
        val blackboardId = this.blackboard.id
        val boardId = this.blackboard.board.id
        
        val links = mutableListOf(
                CollectionLink(
                        rel = "self",
                        href = Uri.forSingleBlackboardItemText(boardId, blackboardId, this.id)
                ),
                CollectionLink(
                        rel = Rels.GET_SINGLE_BLACKBOARD,
                        href = Uri.forSingleBlackboardText(boardId, blackboardId)
                ),
                CollectionLink(
                        rel = Rels.GET_SINGLE_BOARD,
                        href = Uri.forSingleBoardText(boardId)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BLACKBOARDITEMS,
                        href = Uri.forAllBlackboardItems(boardId, blackboardId)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BLACKBOARDS,
                        href = Uri.forAllBlackboards(boardId)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BOARDS,
                        href = Uri.forAllBoards()
                )
        )

        if(this.blackboard.board.creator.id == user.id)
                links.addAll(sequenceOf(
                        CollectionLink(
                                rel = Rels.EDIT_BLACKBOARD,
                                href = Uri.forSingleBlackboardItemText(boardId, blackboardId, this.id)
                        ),
                        CollectionLink(
                                rel = Rels.DELETE_BLACKBOARD,
                                href = Uri.forSingleBlackboardItemText(boardId, blackboardId, this.id)
                        )
                ))
                        
        return Item(
                href = Uri.forSingleBlackboardItemText(boardId, blackboardId, this.id),
                data = listOf(
                        Data(name = "name", value = this.name),
                        Data(name = "id", value = this.id.toString()),
                        Data(name = "content", value = this.content),
                        Data(name = "authorName", value = this.author.name),
                        Data(name = "createdAt", value = this.createdAt.toString())
                ),
                links = links
        )
}