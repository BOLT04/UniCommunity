package pt.isel.g20.unicommunity.forum.model

import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.forumItem.model.PartialForumItemObject
import pt.isel.g20.unicommunity.hateoas.*

class SingleForumResponse(forum: Forum) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = forum.id

    init {
        val board = forum.board
        val boardId = board.id

        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(boardId)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to listOf(partialBoard)
        ))

        if(forum.items.size !=0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_FORUMITEMS to forum.items.map {
                        PartialForumItemObject(
                                it.name,
                                if(it.anonymousPost) null else it.author.name,
                                mapOf("self" to Link(Uri.forSingleForumItemText(boardId, it.id)))
                        )
                    }
            ))
        
        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleForumText(boardId)),

                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(boardId)),
                Rels.CREATE_FORUMITEM to Link(Uri.forSingleForumText(boardId))
        ))
    }
}

class PartialForumObject(
        val _links: Map<String, Link>
) : HalResourceObject()