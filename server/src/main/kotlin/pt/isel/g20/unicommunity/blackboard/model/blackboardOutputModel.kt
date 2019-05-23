package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardResponse(blackboard: Blackboard)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleBlackboardText(blackboard.board.id, blackboard.id)),
                Rels.NAVIGATION to Link("/navigation"),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(blackboard.board.id)),
                Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboards(blackboard.board.id)),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(blackboard.board.id)),
                Rels.EDIT_BLACKBOARD to Link(Uri.forSingleBoardText(blackboard.board.id)),
                Rels.DELETE_BLACKBOARD to Link(Uri.forSingleBoardText(blackboard.board.id))
        )
){
    val boardName: String = blackboard.board.name
    val name : String = blackboard.name
    val description : String? = blackboard.description
    val notificationLevel: String = blackboard.notificationLevel
    val numberOfItems: Int = blackboard.items.size
    val items = blackboard.items
}


class MultipleBlackboardsResponse(
        boardId: Long,
        blackboards : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBlackboards(boardId),
        links = listOf(
                CollectionLink("self",Uri.forAllBlackboards(boardId)),
                CollectionLink(Rels.NAVIGATION, Uri.NAVIGATION_ROUTE)
        ),
        items = blackboards
)

class PartialBlackboardObject(
        val name: String,
        val _links: Map<String, Link>
) : HalResourceObject()