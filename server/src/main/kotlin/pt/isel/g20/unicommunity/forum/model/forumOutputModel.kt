package pt.isel.g20.unicommunity.forum.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleForumResponse(forum: Forum)
    : HalObject(
        mapOf(
                "self" to Link(Uri.forSingleForum(forum.boardId).toString()),
                Rels.NAVIGATION to Link("/navigation"),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(forum.boardId).toString()),
                Rels.CREATE_FORUMITEM to Link(Uri.forSingleForum(forum.boardId).toString())
        )
){
    val allowImagePosts : Boolean = forum.isAllowImagePosts
}