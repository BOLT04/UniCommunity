package pt.isel.g20.unicommunity.blackboardItem.model

import pt.isel.g20.unicommunity.blackboard.model.PartialBlackboardObject
import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject

class SingleBlackboardItemResponse(bbItem: BlackboardItem) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = bbItem.id
    val name = bbItem.name
    val content = bbItem.content
    val createdAt = bbItem.createdAt.toString()

    init{
        val board = bbItem.blackboard.board
        val blackboard = bbItem.blackboard
        val author = bbItem.author
        val boardId = board.id
        val bbId = blackboard.id

        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(boardId)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to listOf(partialBoard)
        ))

        val partialBlackboard = PartialBlackboardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBlackboardText(boardId, bbId)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BLACKBOARD to listOf(partialBlackboard)
        ))

        val partialUser = PartialUserObject(
                bbItem.author.name,
                mapOf("self" to Link(Uri.forSingleUserText(author.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_USER to listOf(partialUser)
        ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBlackboardItemText(boardId, bbId, id )),
                Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboardItems(boardId, bbId)),
                Rels.GET_MULTIPLE_BLACKBOARDITEMS to Link(Uri.forAllBlackboardItems(boardId, bbId)),
                Rels.EDIT_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItemText(boardId, bbId, id)),
                Rels.DELETE_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItemText(boardId, bbId, id))
        ))
    }
}


class MultipleBlackboardItemsResponse(
        boardId: Long,
        bbId: Long,
        blackboards : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBlackboardItems(boardId, bbId),
        links = mutableListOf(
                CollectionLink("self", Rels.GET_MULTIPLE_BLACKBOARDITEMS)
        ),
        items = blackboards
)

class PartialBlackboardItemObject(
        val name: String,
        val author: String,
        val _links: Map<String, Link>
) : HalResourceObject()