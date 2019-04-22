package pt.isel.g20.unicommunity.forum.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleForumResponse(forum: Forum)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleForum(forum.board!!.id).toString()),
                Rels.NAVIGATION to Link("/navigation"),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(forum.board!!.id).toString()),
                Rels.CREATE_FORUMITEM to Link(Uri.forSingleForum(forum.board!!.id).toString())
        )
){
    val allowImagePosts : Boolean = forum.isAllowImagePosts
}