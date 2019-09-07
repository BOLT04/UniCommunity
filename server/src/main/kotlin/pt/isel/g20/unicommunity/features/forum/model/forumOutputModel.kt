package pt.isel.g20.unicommunity.features.forum.model

import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.features.forumItem.model.PartialAnonForumItemObject
import pt.isel.g20.unicommunity.features.forumItem.model.PartialForumItemObject

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
                Rels.GET_SINGLE_BOARD to partialBoard
        ))

        if(forum.items.size !=0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_FORUMITEMS to MultipleHalObj(forum.items.map {
                        if (!it.anonymousPost)
                            PartialForumItemObject(
                                    it.name,
                                    it.content,
                                    it.author.name,
                                    it.createdAt.toString(),
                                    mapOf("self" to Link(Uri.forSingleForumItemText(boardId, it.id)))
                            )
                        else
                            PartialAnonForumItemObject(
                                    it.name,
                                    it.content,
                                    it.createdAt.toString(),
                                    mapOf("self" to Link(Uri.forSingleForumItemText(boardId, it.id)))
                            )
                    })
            ))
        
        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleForumText(boardId)),

                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(boardId)),
                Rels.CREATE_FORUMITEM to Link(Uri.forAllForumItems(boardId)),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(boardId))
        ))
    }
}

class PartialForumObject(
        val _links: Map<String, Link>
) : IHalObj