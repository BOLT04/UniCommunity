package pt.isel.g20.unicommunity.blackboardItem.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardItemResponse(bbItem: BlackboardItem)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleBlackboardItemText(bbItem.blackboard.board.id, bbItem.blackboard.id, bbItem.id )),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.GET_SINGLE_BLACKBOARD to Link(Uri.forSingleBlackboardText(bbItem.blackboard.board.id, bbItem.blackboard.id)),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(bbItem.blackboard.board.id)),
                Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboardItems(bbItem.blackboard.board.id, bbItem.blackboard.id)),
                Rels.GET_MULTIPLE_BLACKBOARDITEMS to Link(Uri.forAllBlackboardItems(bbItem.blackboard.board.id, bbItem.blackboard.id)),
                Rels.EDIT_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItemText(bbItem.blackboard.board.id, bbItem.blackboard.id, bbItem.id)),
                Rels.DELETE_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItemText(bbItem.blackboard.board.id, bbItem.blackboard.id, bbItem.id))
        )
){
    val boardName: String = bbItem.blackboard.board.name
    val blackboardName: String = bbItem.blackboard.name
    val name : String = bbItem.name
    val content : String = bbItem.content
    val authorName: String = bbItem.author.name
    val createdAt: String = bbItem.createdAt.toString()
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