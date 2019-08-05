package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.blackboardItem.model.PartialBlackboardItemObject
import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardResponse(blackboard: Blackboard) : HalObject(){
    val id = blackboard.id
    val name : String = blackboard.name
    val description : String? = blackboard.description
    val notificationLevel: String = blackboard.notificationLevel

    init {
        val board = blackboard.board
        val boardId = board.id
        val blackboardId = blackboard.id

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
                                mapOf("self" to Link(Uri.forSingleBlackboardItemText(boardId, blackboardId, it.id)))
                        )
                    }
            ))
        
        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBlackboardText(boardId, blackboardId)),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(boardId)),
                Rels.EDIT_BLACKBOARD to Link(Uri.forSingleBlackboardText(boardId, blackboardId)),
                Rels.DELETE_BLACKBOARD to Link(Uri.forSingleBlackboardText(boardId, blackboardId)),

                Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboardItems(boardId, blackboardId)),
                Rels.GET_MULTIPLE_BLACKBOARDITEMS to Link(Uri.forAllBlackboardItems(boardId, blackboardId))
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