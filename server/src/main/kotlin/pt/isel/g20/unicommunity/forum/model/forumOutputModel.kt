package pt.isel.g20.unicommunity.forum.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

class SingleForumResponse(forum: Forum)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleForumText(forum.board.id)),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(forum.board.id)),
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(forum.board.id)),
                Rels.CREATE_FORUMITEM to Link(Uri.forSingleForumText(forum.board.id))
        )
){
    val boardName: String = forum.board.name
    val allowImagePosts : Boolean = forum.isAllowImagePosts
    val numberOfPosts: Int = forum.items.size
}