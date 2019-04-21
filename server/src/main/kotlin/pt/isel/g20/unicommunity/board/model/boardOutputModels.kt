package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleBoardResponse(board: Board)
    : HalObject(
        mapOf(
                "self" to Link(Uri.forSingleBoard(board.id).toString()),
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(board.id).toString()),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(board.id).toString()),
                Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForum(board.id).toString())
        )
){
    val name : String = board.name
    val description : String? = board.description
    val templateId: Long = board.templateId
}


class MultipleBoardsResponse(
        boards : Iterable<Board>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBoards().toString(),
        links = listOf(
                CollectionLink("self","/http://localhost:8080/boards"),
                CollectionLink(Rels.NAVIGATION, "/http://localhost:8080/navigation")
        ),
        items = boards.map { Item( Uri.forSingleBoard(it.id).toString()) }
)