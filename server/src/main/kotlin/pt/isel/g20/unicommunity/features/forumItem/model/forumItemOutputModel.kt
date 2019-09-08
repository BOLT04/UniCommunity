package pt.isel.g20.unicommunity.features.forumItem.model

import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.features.comment.model.PartialAnonCommentObject
import pt.isel.g20.unicommunity.features.comment.model.PartialCommentObject
import pt.isel.g20.unicommunity.features.forum.model.PartialForumObject
import pt.isel.g20.unicommunity.features.user.model.PartialUserObject
import pt.isel.g20.unicommunity.features.user.model.User

class SingleForumItemResponse(user: User, forumItem: ForumItem) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = forumItem.id
    val name = forumItem.name
    val content = forumItem.content
    val createdAt = forumItem.getDateFormatted()
    val anonymousPost = forumItem.anonymousPost

    init{
        val board = forumItem.forum.board
        val forum = forumItem.forum
        val author = forumItem.author
        val boardId = board.id
        val isAuthor = user.id == author.id

        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(boardId)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to partialBoard
        ))

        val partialForum = PartialForumObject(
                mapOf("self" to Link(Uri.forSingleForumText(forum.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_FORUM to partialForum
        ))

        if(!anonymousPost){
            val partialUser = PartialUserObject(
                    author.name,
                    author.email,
                    mapOf("self" to Link(Uri.forSingleUserText(author.id)))
            )
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_SINGLE_USER to partialUser
            ))
            super._links?.putAll(sequenceOf(
                    Rels.GET_SINGLE_USER to Link(Uri.forSingleUserText(author.id))
            ))
        }

        if(forumItem.comments.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_COMMENTS to MultipleHalObj(forumItem.comments.map {
                        if (!it.anonymousComment)
                            PartialCommentObject(
                                    it.content,
                                    it.author.name,
                                    it.createdAt.toString(),
                                    mapOf("self" to Link(Uri.forSingleCommentText(boardId, id, it.id)))
                            )
                        else
                            PartialAnonCommentObject(
                                    it.content,
                                    it.createdAt.toString(),
                                    mapOf("self" to Link(Uri.forSingleCommentText(boardId, id, it.id)))
                            )
                    })
            ))
        
        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleForumItemText(boardId, id)),
                Rels.CREATE_FORUMITEM to Link(Uri.forAllForumItems(boardId)),
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(boardId)),
                Rels.GET_MULTIPLE_COMMENTS to Link(Uri.forAllComments(boardId, id)),
                Rels.CREATE_COMMENT to Link(Uri.forAllComments(boardId, id)),
                Rels.CREATE_REPORT to Link(Uri.forAllReports()),
                Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForumText(forum.id)),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(boardId))
        ))

        if(isAuthor){
            super._links?.putAll(sequenceOf(
                    Rels.EDIT_FORUMITEM to Link(Uri.forSingleForumItemText(boardId, id)),
                    Rels.DELETE_FORUMITEM to Link(Uri.forSingleForumItemText(boardId, id))
            ))
        }
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
        val content: String,
        val author: String?,
        val createdAt: String,
        val _links: Map<String, Link>
) : IHalObj

class PartialAnonForumItemObject(
        val name: String,
        val content: String,
        val createdAt: String,
        val _links: Map<String, Link>
) : IHalObj