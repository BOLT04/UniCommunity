package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.blackboardItem.model.PartialBlackboardItemObject
import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardResponse(blackboard: Blackboard)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleBlackboardText(blackboard.board.id, blackboard.id)),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(blackboard.board.id)),
                Rels.EDIT_BLACKBOARD to Link(Uri.forSingleBlackboardText(blackboard.board.id, blackboard.board.id)),
                Rels.DELETE_BLACKBOARD to Link(Uri.forSingleBlackboardText(blackboard.board.id, blackboard.board.id)),

                Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboardItems(blackboard.board.id, blackboard.id)),
                Rels.GET_MULTIPLE_BLACKBOARDITEMS to Link(Uri.forAllBlackboardItems(blackboard.board.id, blackboard.id))

        )
){
    val id = blackboard.id
    val name : String = blackboard.name
    val description : String? = blackboard.description
    val notificationLevel: String = blackboard.notificationLevel

    init {
        val board = blackboard.board
        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(board.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to listOf(partialBoard)
        ))

        if(blackboard.items.size !=0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_BLACKBOARDITEMS to blackboard.items.map {
                        PartialBlackboardItemObject(
                                it.name,
                                it.author.name,
                                mapOf("self" to Link(Uri.forSingleBlackboardItemText(it.blackboard.board.id, it.blackboard.id, it.id)))
                        )
                    }
            ))
    }
}


class MultipleBlackboardsResponse(
        boardId: Long,
        blackboards : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBlackboards(boardId),
        links = mutableListOf(
                CollectionLink("self", Uri.forAllBlackboards(boardId)),
                CollectionLink(Rels.NAVIGATION, Uri.NAVIGATION_ROUTE)
        ),
        items = blackboards
)

class PartialBlackboardObject(
        val name: String,
        val _links: Map<String, Link>
) : HalResourceObject()