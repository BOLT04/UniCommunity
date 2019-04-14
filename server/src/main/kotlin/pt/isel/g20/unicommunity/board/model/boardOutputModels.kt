package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.hateoas.*

class SingleBoardResponse(board: Board)
    : HalObject(
        mapOf(
                "self" to Link(Uri.forSingleBoard(board.id).toString()),
                "states" to Link(Uri.forAllBoards().toString())
        )
){
    val name : String = board.name
    val description : String = board.description
}


class MultipleBoardsResponse(
        Boards : Iterable<Board>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBoards().toString(),
        links = listOf(
                CollectionLink(
                        rel = "/rels/createBoards",
                        href = "/Boards"
                )
        ),
        items = Boards.map { Item( Uri.forSingleBoard(it.id).toString()) }
)