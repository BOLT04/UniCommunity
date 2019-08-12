package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.blackboardItem.model.PartialBlackboardItemObject
import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardResponse(blackboard: Blackboard) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = blackboard.id
    val name = blackboard.name
    val description = blackboard.description
    val notificationLevel = blackboard.notificationLevel

    init {
        val board = blackboard.board
        val boardId = board.id

        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(boardId)))
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
                                mapOf("self" to Link(Uri.forSingleBlackboardItemText(boardId, id, it.id)))
                        )
                    }
            ))
        
        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBlackboardText(boardId, id)),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(boardId)),
                Rels.EDIT_BLACKBOARD to Link(Uri.forSingleBlackboardText(boardId, id)),
                Rels.DELETE_BLACKBOARD to Link(Uri.forSingleBlackboardText(boardId, id)),

                Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboardItems(boardId, id)),
                Rels.GET_MULTIPLE_BLACKBOARDITEMS to Link(Uri.forAllBlackboardItems(boardId, id))
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
                CollectionLink("self", Uri.forAllBlackboards(boardId))
        ),
        items = blackboards
)

class PartialBlackboardObject(
        val name: String,
        val _links: Map<String, Link>
) : HalResourceObject()