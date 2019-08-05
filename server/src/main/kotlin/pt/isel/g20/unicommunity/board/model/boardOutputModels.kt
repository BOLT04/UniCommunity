package pt.isel.g20.unicommunity.board.model

import org.springframework.data.domain.Page
import pt.isel.g20.unicommunity.blackboard.model.PartialBlackboardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.getFirstPageable
import pt.isel.g20.unicommunity.common.getLastPage
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.template.model.PartialTemplateObject
import pt.isel.g20.unicommunity.user.model.PartialUserObject

class SingleBoardResponse(board: Board) : HalObject() {
    val name : String = board.name
    val id : Long = board.id
    val description : String? = board.description

    init {
        val boardId = board.id

        val template = board.template
        val partialTemplate = PartialTemplateObject(
                template.name,
                template.hasForum,
                mapOf("self" to Link(Uri.forSingleTemplateText(template.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_TEMPLATE to listOf(partialTemplate)
        ))

        if(board.blackBoards.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_BLACKBOARDS to board.blackBoards.map {
                        PartialBlackboardObject(
                                it.name,
                                mapOf("self" to Link(Uri.forSingleBlackboardText(boardId, it.id)))
                        )
                    }
            ))

        if(board.members.size !=0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_USERS to board.members.map {
                        PartialUserObject(
                                it.name,
                                mapOf("self" to Link(Uri.forSingleUserText(it.id)))
                        )
                    }
            ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBoardText(boardId)),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(boardId)),
                Rels.CREATE_BOARD to Link(Uri.forAllBoards()),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),
                Rels.EDIT_BOARD to Link(Uri.forSingleBoardText(boardId)),
                Rels.DELETE_BOARD to Link(Uri.forSingleBoardText(boardId)),
                Rels.ADD_MEMBER_TO_BOARD to Link(Uri.forBoardMembers(boardId)),
                Rels.REMOVE_MEMBER_TO_BOARD to Link(Uri.forBoardMembers(boardId))
        ))

        if (board.forum != null) {
            super._links?.putAll(sequenceOf(
                    Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForumText(boardId))
            ))
        }
    }
}


class MultipleBoardsResponse(
        boardsPage : Page<Item>,
        page: Int
): ExtendedJsonCollection(
        totalPages = boardsPage.totalPages,
        version = "1.0",
        href = Uri.forAllBoards(),
        links = mutableListOf(
                CollectionLink("self", Uri.forAllBoards()),
                CollectionLink(Rels.NAVIGATION, Uri.NAVIGATION_ROUTE)
        ),
        items = boardsPage.content,
        queries = listOf(
                Query(
                    href= Uri.forAllBoards(),
                    rel= "self" /*TODO: what rel should we use here?*/,
                    data= listOf(
                            Data(name= "page", value="")
                    )
                )
        )
) {
    init {
        if (page < boardsPage.totalPages && boardsPage.nextPageable().isPaged)
            links?.add(CollectionLink("next", Uri.forAllBoards(page +1)))
        if (!boardsPage.isFirst) {
            links?.add(CollectionLink("prev", Uri.forAllBoards(page - 1)))
            val firstPageable = boardsPage.getFirstPageable()
            links?.add(CollectionLink("first", Uri.forAllBoards(firstPageable.pageNumber)))
        }
        if (!boardsPage.isLast) {
            val lastPage = boardsPage.totalPages -1
            links?.add(CollectionLink("last", Uri.forAllBoards(lastPage)))
        }
    }
}

class PartialBoardObject(
        val name: String,
        val _links: Map<String, Link>
) : HalResourceObject()