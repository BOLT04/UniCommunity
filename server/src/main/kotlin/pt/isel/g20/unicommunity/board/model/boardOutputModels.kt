package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.blackboard.model.PartialBlackboardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject

class SingleBoardResponse(board: Board)
    : HalObject(mutableMapOf(), mutableMapOf()) {
    val name : String = board.name
    val description : String? = board.description
    val templateName: String = board.template.name
    val hasForum: Boolean = board.template.hasForum

    init {
        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBoardText(board.id)),
                Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(board.id)),
                Rels.CREATE_BOARD to Link(Uri.forAllBoards()),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),
                Rels.EDIT_BOARD to Link(Uri.forSingleBoardText(board.id)),
                Rels.DELETE_BOARD to Link(Uri.forSingleBoardText(board.id))
        ))
        if (board.forum != null) {
            super._links?.putAll(sequenceOf(
                Rels.GET_MULTIPLE_FORUMITEMS to Link(Uri.forAllForumItems(board.id)),
                Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForumText(board.id))
            ))
        }

        if(board.blackBoards.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_BLACKBOARDS to board.blackBoards.map {
                        PartialBlackboardObject(it.name,
                                mapOf(
                                        "self" to Link(Uri.forSingleBlackboardText(board.id, it.id))
                                ))
                    })
            )

        if(board.members.size !=0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_USERS to board.members.map {
                        PartialUserObject(it.name,
                                mapOf(
                                        "self" to Link(Uri.forSingleUserText(it.id))
                                ))
                    })
            )
    }
}


class MultipleBoardsResponse(
        boards : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBoards(),
        links = listOf(
                CollectionLink("self","/http://localhost:8080/boards"),
                CollectionLink(Rels.NAVIGATION, "/http://localhost:8080/navigation")
        ),
        items = boards
)

class PartialBoardObject(
        val name: String,
        val _links: Map<String, Link>
) : HalResourceObject()