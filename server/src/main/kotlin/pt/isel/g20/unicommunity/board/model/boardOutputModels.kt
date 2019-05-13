package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.blackboard.model.PartialBlackboardObject
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject
import pt.isel.g20.unicommunity.user.model.User

class SingleBoardResponse(board: Board)
    : HalObject(mutableMapOf(), mutableMapOf()) {
    val name : String = board.name
    val description : String? = board.description
    val templateName: String = board.template.name
    val hasForum: Boolean = board.template.hasForum

    init {
        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBoard(board.id).toString()),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(board.id).toString()),
                Rels.CREATE_BOARD to Link(Uri.forAllBoards().toString()),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards().toString()),
                Rels.EDIT_BOARD to Link(Uri.forSingleBoard(board.id).toString()),
                Rels.DELETE_BOARD to Link(Uri.forSingleBoard(board.id).toString())
        ))
        if (board.forum != null) {
            super._links?.putAll(sequenceOf(
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(board.id).toString()),
                Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForum(board.id).toString())
            ))
        }

        if(board.blackBoards.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_BLACKBOARDS to board.blackBoards.map {
                        PartialBlackboardObject(it.name,
                                mapOf(
                                        "self" to Link(Uri.forSingleBlackboard(board.id, it.id).toString())
                                ))
                    })
            )

        if(board.members.size !=0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_USERS to board.members.map {
                        PartialUserObject(it.name,
                                mapOf(
                                        "self" to Link(Uri.forSingleUser(it.id).toString())
                                ))
                    })
            )
    }
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
        items = boards.map { Item( Uri.forSingleBoard(it.id).toString()) }//TODO: add more info about the board, like its name and description
)

class PartialBoardObject(
        val name: String,
        val _links: Map<String, Link>
) : HalResourceObject()