package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.CollectionLink
import pt.isel.g20.unicommunity.hateoas.Data
import pt.isel.g20.unicommunity.hateoas.Item
import pt.isel.g20.unicommunity.user.model.User

fun Blackboard.toItemRepr(user: User) : Item {
        val boardId = this.board.id
        val links = mutableListOf(
                CollectionLink(
                        rel = "self",
                        href = Uri.forSingleBlackboardText(boardId, this.id)
                ),
                CollectionLink(
                        rel = Rels.GET_SINGLE_BOARD,
                        href = Uri.forSingleBoardText(boardId)
                ),
                CollectionLink(
                        rel = Rels.GET_MULTIPLE_BLACKBOARDITEMS,
                        href = Uri.forAllBlackboardItems(boardId, this.id)
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
        
        if(this.board.creator.id == user.id) 
                links.addAll(sequenceOf(
                        CollectionLink(
                                rel = Rels.EDIT_BLACKBOARD,
                                href = Uri.forSingleBlackboardText(boardId, this.id)
                        ),
                        CollectionLink(
                                rel = Rels.DELETE_BLACKBOARD,
                                href = Uri.forSingleBlackboardText(boardId, this.id)
                        )
                ))
        
        return Item(
                href = Uri.forSingleBlackboardText(boardId, this.id),
                data = listOf(
                        Data(name = "name", value = this.name),
                        Data(name = "id", value = this.id.toString()),
                        Data(name = "description", value = this.description),
                        Data(name = "notificationLevel", value = this.notificationLevel)
                ),
                links = links
        )
}