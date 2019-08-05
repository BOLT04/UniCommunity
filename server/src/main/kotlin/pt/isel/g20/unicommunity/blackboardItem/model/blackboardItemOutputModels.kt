package pt.isel.g20.unicommunity.blackboardItem.model

import pt.isel.g20.unicommunity.blackboard.model.PartialBlackboardObject
import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject

class SingleBlackboardItemResponse(bbItem: BlackboardItem)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleBlackboardItemText(bbItem.blackboard.board.id, bbItem.blackboard.id, bbItem.id )),
                Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboardItems(bbItem.blackboard.board.id, bbItem.blackboard.id)),
                Rels.GET_MULTIPLE_BLACKBOARDITEMS to Link(Uri.forAllBlackboardItems(bbItem.blackboard.board.id, bbItem.blackboard.id)),
                Rels.EDIT_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItemText(bbItem.blackboard.board.id, bbItem.blackboard.id, bbItem.id)),
                Rels.DELETE_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItemText(bbItem.blackboard.board.id, bbItem.blackboard.id, bbItem.id))
        )
){
    val name : String = bbItem.name
    val content : String = bbItem.content
    val createdAt: String = bbItem.createdAt.toString()

    init{
        val board = bbItem.blackboard.board
        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(board.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to listOf(partialBoard)
        ))

        val blackboard = bbItem.blackboard
        val partialBlackboard = PartialBlackboardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBlackboardText(board.id,blackboard.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BLACKBOARD to listOf(partialBlackboard)
        ))

        val author = bbItem.author
        val partialUser = PartialUserObject(
                bbItem.name,
                mapOf("self" to Link(Uri.forSingleUserText(author.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to listOf(partialUser)
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
                CollectionLink("self", Rels.GET_MULTIPLE_BLACKBOARDITEMS),
                CollectionLink(Rels.NAVIGATION, Uri.NAVIGATION_ROUTE)
        ),
        items = blackboards
)

class PartialBlackboardItemObject(
        val name: String,
        val author: String,
        val _links: Map<String, Link>
) : HalResourceObject()