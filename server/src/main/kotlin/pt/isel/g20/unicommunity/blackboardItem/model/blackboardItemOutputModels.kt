package pt.isel.g20.unicommunity.blackboardItem.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardItemResponse(bbItem: BlackboardItem)
    : HalObject(
        mapOf(
                "self" to Link(Uri.forSingleBlackboardItem(bbItem.boardId, bbItem.bbId, bbItem.id ).toString()),
                "states" to Link(Uri.forAllBlackboardItems(bbItem.boardId, bbItem.bbId).toString())
        )
){
    val name : String = bbItem.name
    val content : String = bbItem.content
}


class MultipleBlackboardItemsResponse(
        boardId: Long,
        bbId: Long,
        Blackboards : Iterable<BlackboardItem>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBlackboardItems(boardId, bbId).toString(),
        links = listOf(
                CollectionLink(
                        rel = "/rels/createSubmissions",
                        href = "/submissions"
                )
        ),
        items = Blackboards.map { Item( Uri.forSingleBlackboardItem(it.boardId, it.bbId, it.id).toString()) }
)