package pt.isel.g20.unicommunity.comment.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleCommentResponse(comment: Comment)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleComment(
                        comment.forumItem!!.forum!!.board!!.id,
                        comment.forumItem!!.id,
                        comment.id
                ).toString()),
                Rels.GET_MULTIPLE_COMMENTS to Link(Uri.forAllComments(
                        comment.forumItem!!.forum!!.board!!.id,
                        comment.forumItem!!.id
                ).toString()),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(comment.forumItem!!.forum!!.board!!.id).toString())
        )
){
    val forumItemName: String = comment.forumItem!!.name
    val content : String = comment.content
    val createdAt: String = comment.createdAt.toString()
}


class MultipleCommentsResponse(
        boardId: Long,
        forumItemId: Long,
        comments : Iterable<Comment>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllComments(boardId,forumItemId).toString(),
        links = listOf(
                CollectionLink("self","http://localhost:8080/boards/$boardId/forum/posts/$forumItemId/comments"),
                CollectionLink(Rels.NAVIGATION, "http://localhost:8080/navigation"),
                CollectionLink(Rels.CREATE_COMMENT, "http://localhost:8080"+Uri.forAllComments(boardId, forumItemId).toString())
        ),
        items = comments.map { Item( Uri.forSingleComment(
                it.forumItem!!.forum!!.board!!.id,
                it.forumItem!!.id,
                it.id
        ).toString()) }
)