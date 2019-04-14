package pt.isel.g20.unicommunity.blackBlackboard.model

import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.hateoas.*

class SingleBlackboardResponse(blackboard: Blackboard)
    : HalObject(
        mapOf(
                "self" to Link(Uri.forSingleBlackboard(blackboard.boardId, blackboard.id).toString()),
                "states" to Link(Uri.forAllBlackboards(blackboard.boardId).toString())
        )
){
    val name : String = blackboard.name
    val description : String = blackboard.description
    val notificationLevel: String = blackboard.notificationLevel
}


class MultipleBlackboardsResponse(
        boardId: Long,
        Blackboards : Iterable<Blackboard>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBlackboards(boardId).toString(),
        links = listOf(
                CollectionLink(
                        rel = "/rels/createBlackboards",
                        href = "/Blackboards"
                )
        ),
        items = Blackboards.map { Item( Uri.forSingleBlackboard(it.boardId, it.id).toString()) }
)