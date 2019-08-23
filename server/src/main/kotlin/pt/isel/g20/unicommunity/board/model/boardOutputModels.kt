package pt.isel.g20.unicommunity.board.model

import org.springframework.data.domain.Page
import pt.isel.g20.unicommunity.blackboard.model.PartialBlackboardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.getFirstPageable
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject
import pt.isel.g20.unicommunity.user.model.User

class SingleBoardResponse(user: User, board: Board) : HalObject(mutableMapOf(), mutableMapOf()) {
    val id : Long = board.id
    val name : String = board.name
    val description : String? = board.description

    init {

        val isCreator = user.id == board.creator.id
        if(board.blackBoards.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_BLACKBOARDS to MultipleHalObj(board.blackBoards.map {
                        PartialBlackboardObject(
                                it.name,
                                mapOf("self" to Link(Uri.forSingleBlackboardText(id, it.id)))
                        )
                    })
            ))

        if(board.members.size !=0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_USERS to MultipleHalObj(board.members.map {
                        PartialUserObject(
                                it.name,
                                mapOf("self" to Link(Uri.forSingleUserText(it.id)))
                        )
                    })
            ))

        val partialUser = PartialUserObject(
                board.creator.name,
                mapOf("self" to Link(Uri.forSingleUserText(board.creator.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_USER to partialUser
        ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBoardText(id)),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),

                Rels.GET_BOARD_MEMBERS to Link(Uri.forBoardMembers(id)),
                Rels.SUBSCRIBE to Link(Uri.forBoardMembers(id)),
                Rels.UNSUBSCRIBE to Link(Uri.forBoardMembers(id)),

                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(id))
        ))

        if(isCreator){
            super._links?.putAll(sequenceOf(
                    Rels.EDIT_BOARD to Link(Uri.forSingleBoardText(id)),
                    Rels.DELETE_BOARD to Link(Uri.forSingleBoardText(id))
            ))
        }

        if (board.forum != null) {
            super._links?.putAll(sequenceOf(
                    Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForumText(id))
            ))
        }
    }
}


class MultipleBoardsResponse(
        boardsPage : Page<Item>,
        page: Int
): ExtendedJsonCollection(
        totalPages = boardsPage.totalPages -1,
        version = "1.0",
        href = Uri.forAllBoards(),
        links = mutableListOf(
                CollectionLink("self", Uri.forAllBoards())
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

class MultipleBoardsResponseWithoutPagination(
        boards : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllBoards(),
        links = mutableListOf(
                CollectionLink("self", Uri.forAllBoards())
        ),
        items = boards,
        queries = listOf(
                Query(
                        href= Uri.forAllBoards(),
                        rel= "self" /*TODO: what rel should we use here?*/,
                        data= listOf()
                )
        )
)

class PartialBoardObject(
        val name: String,
        val _links: Map<String, Link>
) : IHalObj