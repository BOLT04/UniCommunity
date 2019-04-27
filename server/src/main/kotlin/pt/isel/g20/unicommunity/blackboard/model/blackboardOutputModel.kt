package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardResponse(blackboard: Blackboard)
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.forSingleBlackboard(blackboard.board!!.id, blackboard.id).toString()),
                Rels.NAVIGATION to Link("/navigation"),
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoard(blackboard.board!!.id).toString()),
                Rels.CREATE_BLACKBOARDITEM to Link(Uri.forAllBlackboards(blackboard.board!!.id).toString()),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(blackboard.board!!.id).toString()),
                Rels.EDIT_BLACKBOARD to Link(Uri.forSingleBoard(blackboard.board!!.id).toString()),
                Rels.DELETE_BLACKBOARD to Link(Uri.forSingleBoard(blackboard.board!!.id).toString())
        )
){
    val name : String = blackboard.name
    val description : String? = blackboard.description
    val notificationLevel: String = blackboard.notificationLevel
    val items = blackboard.items
}


class MultipleBlackboardsResponse(
        boardId: Long,
        blackboards : Iterable<Blackboard>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBlackboards(boardId).toString(),
        links = listOf(
                CollectionLink("self","/http://localhost:3000/boards/$boardId/blackboards"),
                CollectionLink(Rels.NAVIGATION, "/http://localhost:3000/navigation")
        ),
        items = blackboards.map { Item( Uri.forSingleBlackboard(it.board!!.id, it.id).toString()) }
)

class PartialBlackboardObject(
        val name: String,
        val _links: Map<String, Link>
) : HalResourceObject()