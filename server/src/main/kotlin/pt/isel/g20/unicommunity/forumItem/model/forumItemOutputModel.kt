package pt.isel.g20.unicommunity.forumItem.model

import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.comment.model.PartialCommentObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject

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
    val name : String = forumItem.name
    val content : String = forumItem.content
    val createdAt : String = forumItem.createdAt.toString()

    init{
        val board = forumItem.forum.board
        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(board.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to listOf(partialBoard)
        ))

        val author = forumItem.author
        val partialUser = PartialUserObject(
                author.name,
                mapOf("self" to Link(Uri.forSingleUserText(author.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to listOf(partialUser)
        ))

        if(forumItem.comments.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_COMMENTS to forumItem.comments.map {
                        PartialCommentObject(
                                it.content,
                                if(it.anonymousComment) null else it.author.name,
                                mapOf("self" to Link(Uri.forSingleCommentText(board.id, forumItem.id, it.id)))
                        )
                    }
            ))
    }
}


class MultipleForumItemsResponse(
        boardId: Long,
        forumItems : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllForumItems(boardId),
        links = mutableListOf(
                CollectionLink("self", Uri.forAllForumItems(boardId)),
                CollectionLink(Rels.NAVIGATION, Uri.NAVIGATION_ROUTE),
                CollectionLink(Rels.CREATE_FORUMITEM, Uri.forAllForumItems(boardId))
        ),
        items = forumItems
)

class PartialForumItemObject(
        val name: String,
        val author: String?,
        val _links: Map<String, Link>
) : HalResourceObject()