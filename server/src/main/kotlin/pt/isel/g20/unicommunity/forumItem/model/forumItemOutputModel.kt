package pt.isel.g20.unicommunity.blackBlackboard.model

import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.hateoas.*

class SingleForumItemResponse(forumItem: ForumItem)
    : HalObject(
        mapOf(
                "self" to Link(Uri.forSingleForumItem(forumItem.boardId, forumItem.id).toString()),
                "states" to Link(Uri.forAllForumItems(forumItem.boardId).toString())
        )
){
    val name : String = forumItem.name
    val content : String = forumItem.content
}


class MultipleForumItemsResponse(
        boardId: Long,
        forumItems : Iterable<ForumItem>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllForumItems(boardId).toString(),
        links = listOf(
                CollectionLink(
                        rel = "/rels/createForumItems",
                        href = "/ForumItems"
                )
        ),
        items = forumItems.map { Item( Uri.forSingleForumItem(it.boardId, it.id).toString()) }
)