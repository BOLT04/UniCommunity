package pt.isel.g20.unicommunity.blackboardItem.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardItemResponse(bbItem: BlackboardItem)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleBlackboardItem(bbItem.blackboard!!.board!!.id, bbItem.blackboard!!.id, bbItem.id ).toString()),
                Rels.GET_SINGLE_BLACKBOARD to Link(Uri.forSingleBlackboard(bbItem.blackboard!!.board!!.id, bbItem.blackboard!!.id).toString()),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(bbItem.blackboard!!.board!!.id).toString()),
                Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboardItems(bbItem.blackboard!!.board!!.id, bbItem.blackboard!!.id).toString()),
                Rels.GET_MULTIPLE_BLACKBOARDITEMS to Link(Uri.forAllBlackboardItems(bbItem.blackboard!!.board!!.id, bbItem.blackboard!!.id).toString()),
                Rels.EDIT_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItem(bbItem.blackboard!!.board!!.id, bbItem.blackboard!!.id, bbItem.id).toString()),
                Rels.DELETE_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItem(bbItem.blackboard!!.board!!.id, bbItem.blackboard!!.id, bbItem.id).toString())
        )
){
    val name : String = bbItem.name
    val content : String = bbItem.content
    val author: String = bbItem.author
    val createdAt: String = bbItem.createdAt.toString()
}


class MultipleBlackboardItemsResponse(
        boardId: Long,
        bbId: Long,
        blackboards : Iterable<BlackboardItem>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBlackboardItems(boardId, bbId).toString(),
        links = listOf(
                CollectionLink("self","/http://localhost:3000/boards/$boardId/blackboards/$bbId/submissions"),
                CollectionLink(Rels.NAVIGATION, "/http://localhost:3000/navigation")
        ),
        items = blackboards.map { Item( Uri.forSingleBlackboardItem(it.blackboard!!.board!!.id, it.blackboard!!.id, it.id).toString()) }
)