package pt.isel.g20.unicommunity.blackBlackboard.model

import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.hateoas.*

class SingleForumItemResponse(forumItem: ForumItem)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleForumItem(forumItem.forum!!.board!!.id, forumItem.id).toString()),
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(forumItem.forum!!.board!!.id).toString()),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(forumItem.forum!!.board!!.id).toString())
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
                CollectionLink("self","http://localhost:8080/boards/$boardId/forum/posts"),//TODO: decide posts or submissions
                CollectionLink(Rels.NAVIGATION, "http://localhost:8080/navigation"), //TODO: Clean up hardcoded string and prefix with localhost etc...and this is maybe later configured (domain and port) in application.properties
                CollectionLink(Rels.CREATE_FORUMITEM, "http://localhost:8080"+Uri.forAllForumItems(boardId).toString())
        ),
        items = forumItems.map { Item( Uri.forSingleForumItem(it.forum!!.board!!.id, it.id).toString()) }
)