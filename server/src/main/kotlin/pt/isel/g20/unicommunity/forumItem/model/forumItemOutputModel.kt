package pt.isel.g20.unicommunity.forumItem.model

import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.comment.model.PartialCommentObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.forum.model.PartialForumObject
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject

class SingleForumItemResponse(forumItem: ForumItem) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = forumItem.id
    val name = forumItem.name
    val content = forumItem.content
    val createdAt = forumItem.createdAt.toString()

    init{
        val board = forumItem.forum.board
        val forum = forumItem.forum
        val author = forumItem.author
        val boardId = board.id

        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(boardId)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to SingleHalObj(partialBoard)
        ))

        val partialForum = PartialForumObject(
                mapOf("self" to Link(Uri.forSingleForumText(forum.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_FORUM to SingleHalObj(partialForum)
        ))

        val partialUser = PartialUserObject(
                author.name,
                mapOf("self" to Link(Uri.forSingleUserText(author.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_USER to SingleHalObj(partialUser)
        ))

        if(forumItem.comments.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_COMMENTS to MultipleHalObj(forumItem.comments.map {
                        PartialCommentObject(
                                it.content,
                                if(it.anonymousComment) null else it.author.name,
                                mapOf("self" to Link(Uri.forSingleCommentText(boardId, id, it.id)))
                        )
                    })
            ))
        
        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleForumItemText(boardId, id)),
                Rels.CREATE_FORUMITEM to Link(Uri.forAllForumItems(boardId)),
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(boardId)),
                Rels.EDIT_FORUMITEM to Link(Uri.forSingleForumItemText(boardId, id)),
                Rels.DELETE_FORUMITEM to Link(Uri.forSingleForumItemText(boardId, id)),

                Rels.GET_MULTIPLE_COMMENTS to Link(Uri.forAllComments(boardId, id)),
                Rels.CREATE_COMMENT to Link(Uri.forAllComments(boardId, id))
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
                CollectionLink(Rels.CREATE_FORUMITEM, Uri.forAllForumItems(boardId))
        ),
        items = forumItems
)

class PartialForumItemObject(
        val name: String,
        val author: String?,
        val _links: Map<String, Link>
) : HalResourceObject()