package pt.isel.g20.unicommunity.features.blackboardItem.model

import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.blackboard.model.PartialBlackboardObject
import pt.isel.g20.unicommunity.features.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.features.user.model.PartialUserObject
import pt.isel.g20.unicommunity.features.user.model.User

class SingleBlackboardItemResponse(user: User, bbItem: BlackboardItem) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = bbItem.id
    val name = bbItem.name
    val content = bbItem.content
    val createdAt = bbItem.getDateFormatted()

    init{
        val board = bbItem.blackboard.board
        val blackboard = bbItem.blackboard
        val author = bbItem.author
        val boardId = board.id
        val bbId = blackboard.id
        val isCreator = user.id == board.creator.id

        val partialBoard = PartialBoardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBoardText(boardId)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to partialBoard
        ))

        val partialBlackboard = PartialBlackboardObject(
                board.name,
                mapOf("self" to Link(Uri.forSingleBlackboardText(boardId, bbId)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BLACKBOARD to partialBlackboard
        ))

        val partialUser = PartialUserObject(
                bbItem.author.name,
                bbItem.author.email,
                mapOf("self" to Link(Uri.forSingleUserText(author.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_USER to partialUser
        ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBlackboardItemText(boardId, bbId, id)),
                Rels.GET_MULTIPLE_BLACKBOARDITEMS to Link(Uri.forAllBlackboardItems(boardId, bbId)),
                Rels.GET_SINGLE_USER to Link(Uri.forSingleUserText(author.id)),
                Rels.GET_SINGLE_BLACKBOARD to Link(Uri.forSingleBlackboardText(boardId, bbId)),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(boardId))
        ))

        if(isCreator){
            super._links?.putAll(sequenceOf(
                    Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboardItems(boardId, bbId)),
                    Rels.EDIT_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItemText(boardId, bbId, id)),
                    Rels.DELETE_BLACKBOARDITEM to Link(Uri.forSingleBlackboardItemText(boardId, bbId, id))
            ))
        }
    }
}


class MultipleBlackboardItemsResponse(
        boardId: Long,
        bbId: Long,
        blackboards : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBlackboardItems(boardId, bbId),
        links = mutableListOf(
                CollectionLink("self", Rels.GET_MULTIPLE_BLACKBOARDITEMS)
        ),
        items = blackboards
)

class PartialBlackboardItemObject(
        val name: String,
        val author: String,
        val createdAt: String,
        val _links: Map<String, Link>
) : IHalObj