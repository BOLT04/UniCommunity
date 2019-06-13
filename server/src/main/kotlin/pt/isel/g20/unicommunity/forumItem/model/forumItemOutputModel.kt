package pt.isel.g20.unicommunity.forumItem.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

class SingleForumItemResponse(forumItem: ForumItem)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleForumItemText(forumItem.forum.board.id, forumItem.id)),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(forumItem.forum.board.id)),
                Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForumText(forumItem.forum.board.id)),
                Rels.CREATE_FORUMITEM to Link(Uri.forAllForumItems(forumItem.forum.board.id)),
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(forumItem.forum.board.id)),
                Rels.EDIT_FORUMITEM to Link(Uri.forSingleForumItemText(forumItem.forum.board.id, forumItem.id)),
                Rels.DELETE_FORUMITEM to Link(Uri.forSingleForumItemText(forumItem.forum.board.id, forumItem.id)),

                Rels.GET_MULTIPLE_COMMENTS to Link(Uri.forAllComments(forumItem.forum.board.id, forumItem.id)),
                Rels.CREATE_COMMENT to Link(Uri.forAllComments(forumItem.forum.board.id, forumItem.id))
        )
){
    val boardName: String = forumItem.forum.board.name
    val name : String = forumItem.name
    val content : String = forumItem.content
    var authorName : String? = forumItem.author.name
    val createdAt : String = forumItem.createdAt.toString()

    init {
        if(forumItem.anonymousPost)
            authorName = null
    }
}


class MultipleForumItemsResponse(
        boardId: Long,
        forumItems : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllForumItems(boardId),
        links = listOf(
                CollectionLink("self", Uri.forAllForumItems(boardId)),
                CollectionLink(Rels.NAVIGATION, Uri.NAVIGATION_ROUTE),
                CollectionLink(Rels.CREATE_FORUMITEM, Uri.forAllForumItems(boardId))
        ),
        items = forumItems
)