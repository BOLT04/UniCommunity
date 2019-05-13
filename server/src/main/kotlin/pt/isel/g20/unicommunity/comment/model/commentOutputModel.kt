package pt.isel.g20.unicommunity.comment.model

import pt.isel.g20.unicommunity.hateoas.*
import java.util.*

class SingleCommentResponse(comment: Comment)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleComment(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id,
                        comment.id
                ).toString()),
                Rels.NAVIGATION to Link("/navigation"),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(comment.forumItem.forum.board.id).toString()),
                Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForum(comment.forumItem.forum.board.id).toString()),
                Rels.GET_SINGLE_FORUMITEM to Link(Uri.forSingleForumItem(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id
                ).toString()),
                Rels.CREATE_COMMENT to Link(Uri.forAllComments(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id
                ).toString()),
                Rels.GET_MULTIPLE_COMMENTS to Link(Uri.forSingleComment(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id,
                        comment.id
                ).toString()),
                Rels.EDIT_COMMENT to Link(Uri.forSingleComment(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id,
                        comment.id
                ).toString()),
                Rels.DELETE_COMMENT to Link(Uri.forSingleComment(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id,
                        comment.id
                ).toString())
        )
){
    val boardName: String = comment.forumItem.forum.board.name
    val forumItemName: String = comment.forumItem.name
    val authorName: String = comment.author.name
    val content : String = comment.content
    val createdAt: String = comment.createdAt!!.toString()
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
                it.forumItem.forum.board.id,
                it.forumItem.id,
                it.id
        ).toString()) }
)