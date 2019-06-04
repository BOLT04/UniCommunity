package pt.isel.g20.unicommunity.comment.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

class SingleCommentResponse(comment: Comment)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleCommentText(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id,
                        comment.id
                )),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(comment.forumItem.forum.board.id)),
                Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForumText(comment.forumItem.forum.board.id)),
                Rels.GET_SINGLE_FORUMITEM to Link(Uri.forSingleForumItemText(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id
                )),
                Rels.CREATE_COMMENT to Link(Uri.forAllComments(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id
                )),
                Rels.GET_MULTIPLE_COMMENTS to Link(Uri.forSingleCommentText(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id,
                        comment.id
                )),
                Rels.EDIT_COMMENT to Link(Uri.forSingleCommentText(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id,
                        comment.id
                )),
                Rels.DELETE_COMMENT to Link(Uri.forSingleCommentText(
                        comment.forumItem.forum.board.id,
                        comment.forumItem.id,
                        comment.id
                ))
        )
){
    val boardName: String = comment.forumItem.forum.board.name
    val forumItemName: String = comment.forumItem.name
    val authorName: String = comment.author.name
    val content : String = comment.content
    val createdAt: String = comment.createdAt.toString()
}


class MultipleCommentsResponse(
        boardId: Long,
        forumItemId: Long,
        comments : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllComments(boardId,forumItemId),
        links = listOf(
                CollectionLink("self",Uri.forAllComments(boardId, forumItemId)),
                CollectionLink(Rels.NAVIGATION, Uri.NAVIGATION_ROUTE),
                CollectionLink(Rels.CREATE_COMMENT, Uri.forAllComments(boardId, forumItemId))
        ),
        items = comments
)