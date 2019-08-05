package pt.isel.g20.unicommunity.comment.model

import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.forumItem.model.PartialForumItemObject
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject

class SingleCommentResponse(comment: Comment)
    : HalObject(){
    val content : String = comment.content
    val createdAt: String = comment.createdAt.toString()

    init{
        val board = comment.forumItem.forum.board
        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(board.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to listOf(partialBoard)
        ))

        val forumItem = comment.forumItem
        val partialForumItem = PartialForumItemObject(
                forumItem.name,
                if(comment.anonymousComment) null else forumItem.author.name,
                mapOf("self" to Link(Uri.forSingleForumItemText(board.id, forumItem.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_FORUMITEM to listOf(partialForumItem)
        ))

        val author = comment.author
        val partialUser = PartialUserObject(
                author.name,
                mapOf("self" to Link(Uri.forSingleUserText(author.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_USER to listOf(partialUser)
        ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleCommentText(
                        board.id,
                        forumItem.id,
                        comment.id
                )),
                Rels.GET_SINGLE_FORUMITEM to Link(Uri.forSingleForumItemText(
                        board.id,
                        forumItem.id
                )),
                Rels.CREATE_COMMENT to Link(Uri.forAllComments(
                        board.id,
                        forumItem.id
                )),
                Rels.GET_MULTIPLE_COMMENTS to Link(Uri.forAllComments(
                        board.id,
                        forumItem.id
                )),
                Rels.EDIT_COMMENT to Link(Uri.forSingleCommentText(
                        board.id,
                        forumItem.id,
                        comment.id
                )),
                Rels.DELETE_COMMENT to Link(Uri.forSingleCommentText(
                        board.id,
                        forumItem.id,
                        comment.id
                ))
        ))
    }
}


class MultipleCommentsResponse(
        boardId: Long,
        forumItemId: Long,
        comments : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllComments(boardId,forumItemId),
        links = mutableListOf(
                CollectionLink("self",Uri.forAllComments(boardId, forumItemId)),
                CollectionLink(Rels.NAVIGATION, Uri.NAVIGATION_ROUTE),
                CollectionLink(Rels.CREATE_COMMENT, Uri.forAllComments(boardId, forumItemId))
        ),
        items = comments
)

class PartialCommentObject(
        val content: String,
        val author: String?,
        val _links: Map<String, Link>
) : HalResourceObject()