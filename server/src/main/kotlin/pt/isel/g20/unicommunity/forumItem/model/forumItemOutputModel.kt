package pt.isel.g20.unicommunity.blackBlackboard.model

import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.hateoas.*

class SingleForumItemResponse(forumItem: ForumItem)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleForumItem(forumItem.boardId, forumItem.id).toString()),
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(forumItem.boardId).toString()),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(forumItem.boardId).toString())
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
                CollectionLink("self","/http://localhost:3000/boards/$boardId/forum/submissions"),
                CollectionLink(Rels.NAVIGATION, "/http://localhost:3000/navigation")
        ),
        items = forumItems.map { Item( Uri.forSingleForumItem(it.boardId, it.id).toString()) }
)