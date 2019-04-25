package pt.isel.g20.unicommunity.forum.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleForumResponse(forum: Forum)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleForum(forum.board!!.id).toString()),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(forum.board!!.id).toString()),
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(forum.board!!.id).toString()),
                Rels.CREATE_FORUMITEM to Link(Uri.forSingleForum(forum.board!!.id).toString())
        //TODO: add link to go to the forum items
        )
){
    val allowImagePosts : Boolean = forum.isAllowImagePosts
}