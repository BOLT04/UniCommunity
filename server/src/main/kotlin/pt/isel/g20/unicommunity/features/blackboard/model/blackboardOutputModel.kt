package pt.isel.g20.unicommunity.features.blackboard.model

import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.blackboardItem.model.PartialBlackboardItemObject
import pt.isel.g20.unicommunity.features.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.features.user.model.User

class SingleBlackboardResponse(user: User, blackboard: Blackboard) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = blackboard.id
    val name = blackboard.name
    val description = blackboard.description
    val notificationLevel = blackboard.notificationLevel

    init {
        val board = blackboard.board
        val boardId = board.id
        val isCreator = user.id == board.creator.id

        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(boardId)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to partialBoard
        ))

        if(blackboard.items.size !=0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_BLACKBOARDITEMS to MultipleHalObj(blackboard.items.map {
                        PartialBlackboardItemObject(
                                it.name,
                                it.author.name,
                                it.createdAt.toString(),
                                mapOf("self" to Link(Uri.forSingleBlackboardItemText(boardId, id, it.id)))
                        )
                    })
            ))
        
        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBlackboardText(boardId, id)),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(boardId)),
                Rels.GET_MULTIPLE_BLACKBOARDITEMS to Link(Uri.forAllBlackboardItems(boardId, id)),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(boardId))
        ))

        if(isCreator){
            super._links?.putAll(sequenceOf(
                    Rels.EDIT_BLACKBOARD to Link(Uri.forSingleBlackboardText(boardId, id)),
                    Rels.DELETE_BLACKBOARD to Link(Uri.forSingleBlackboardText(boardId, id)),
                    Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboardItems(boardId, id))
            ))
        }
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
) : IHalObj