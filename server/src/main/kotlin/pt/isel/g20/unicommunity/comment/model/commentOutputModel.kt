package pt.isel.g20.unicommunity.comment.model

import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.forumItem.model.PartialForumItemObject
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject
import pt.isel.g20.unicommunity.user.model.User

class SingleCommentResponse(user: User, comment: Comment) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = comment.id
    val content  = comment.content
    val createdAt = comment.createdAt.toString()
    val anonymousComment = comment.anonymousComment

    init{
        val board = comment.forumItem.forum.board
        val forumItem = comment.forumItem
        val author = comment.author
        val boardId = board.id
        val forumItemId = forumItem.id
        val isAuthor = user.id == comment.author.id

        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(boardId)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to partialBoard
        ))

        if(!comment.anonymousComment){
            val partialForumItem = PartialForumItemObject(
                    forumItem.name,
                    forumItem.content,
                    forumItem.author.name,
                    forumItem.createdAt.toString(),
                    mapOf("self" to Link(Uri.forSingleForumItemText(boardId, forumItemId)))
            )
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_SINGLE_FORUMITEM to partialForumItem
            ))
        }

        if(!anonymousComment){
            val partialUser = PartialUserObject(
                    author.name,
                    author.email,
                    mapOf("self" to Link(Uri.forSingleUserText(author.id)))
            )
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_SINGLE_USER to partialUser
            ))
        }

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleCommentText(
                        boardId,
                        forumItemId,
                        id
                )),
                Rels.GET_SINGLE_FORUMITEM to Link(Uri.forSingleForumItemText(
                        boardId,
                        forumItemId
                )),
                Rels.CREATE_COMMENT to Link(Uri.forAllComments(
                        boardId,
                        forumItemId
                )),
                Rels.GET_MULTIPLE_COMMENTS to Link(Uri.forAllComments(
                        boardId,
                        forumItemId
                ))
        ))

        if(isAuthor){
            super._links?.putAll(sequenceOf(
                    Rels.EDIT_COMMENT to Link(Uri.forSingleCommentText(
                            boardId,
                            forumItemId,
                            id
                    )),
                    Rels.DELETE_COMMENT to Link(Uri.forSingleCommentText(
                            boardId,
                            forumItemId,
                            id
                    ))
            ))
        }
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
                CollectionLink(Rels.CREATE_COMMENT, Uri.forAllComments(boardId, forumItemId))
        ),
        items = comments
)

class PartialCommentObject(
        val content: String,
        val author: String?,
        val createdAt: String,
        val _links: Map<String, Link>
) : IHalObj

