package pt.isel.g20.unicommunity.blackboardItem.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardItemResponse(bbItem: BlackboardItem)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleBlackboardItem(bbItem.boardId, bbItem.bbId, bbItem.id ).toString()),
                Rels.GET_SINGLE_BLACKBOARD to Link(Uri.forSingleBlackboard(bbItem.boardId, bbItem.bbId).toString()),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(bbItem.boardId).toString())
        )
){
    val name : String = bbItem.name
    val content : String = bbItem.content
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
        items = blackboards.map { Item( Uri.forSingleBlackboardItem(it.boardId, it.bbId, it.id).toString()) }
)