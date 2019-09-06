package pt.isel.g20.unicommunity.features.board.model

import org.springframework.data.domain.Page
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.blackboard.model.PartialBlackboardObject
import pt.isel.g20.unicommunity.features.user.model.PartialUserObject
import pt.isel.g20.unicommunity.features.user.model.User

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

        if(board.usersBoards.size !=0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_USERS to MultipleHalObj(board.getMembers().map {
                        PartialUserObject(
                                it.name,
                                it.email,
                                mapOf("self" to Link(Uri.forSingleUserText(it.id)))
                        )
                    })
            ))

        val partialUser = PartialUserObject(
                board.creator.name,
                board.creator.email,
                mapOf("self" to Link(Uri.forSingleUserText(board.creator.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_USER to partialUser
        ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleBoardText(id)),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),

                Rels.GET_BOARD_MEMBERS to Link(Uri.forBoardMembers(id)),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(id)),
                Rels.GET_SINGLE_USER to Link(Uri.forSingleUserText(board.creator.id))
        ))

        if(isCreator){
            super._links?.putAll(sequenceOf(
                    Rels.EDIT_BOARD to Link(Uri.forSingleBoardText(id)),
                    Rels.DELETE_BOARD to Link(Uri.forSingleBoardText(id))
            ))
        }

        if(board.getMembers().find { it.id == user.id } != null)
            super._links?.putAll(sequenceOf(Rels.UNSUBSCRIBE to Link(Uri.forBoardMembers(id))))
        else
            super._links?.putAll(sequenceOf(Rels.SUBSCRIBE to Link(Uri.forBoardMembers(id))))

        if(user.role == TEACHER || user.role == ADMIN)
            super._links?.putAll(sequenceOf(
                    Rels.CREATE_BOARD to Link(Uri.forAllBoards())
            ))

        if (board.forum != null) {
            super._links?.putAll(sequenceOf(
                    Rels.GET_SINGLE_FORUM to Link(Uri.forSingleForumText(id))
            ))
        }
    }
}

class SubscribeResponse(board: Board) : HalObject(mutableMapOf(), mutableMapOf()) {
    val topics = board.blackBoards.map { it.getFcmTopicName() }
    init {
        val boardId = board.id
        super._links?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(boardId)),
                Rels.GET_BOARD_MEMBERS to Link(Uri.forBoardMembers(boardId)),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(boardId)),
                Rels.GET_SINGLE_USER to Link(Uri.forSingleUserText(board.creator.id)),
                Rels.UNSUBSCRIBE to Link(Uri.forBoardMembers(boardId))
        ))
    }
}


class UnsubscribeResponse(board: Board) : HalObject(mutableMapOf(), mutableMapOf()) {
    val topics = board.blackBoards.map { it.getFcmTopicName() }
    init {
        val boardId = board.id
        super._links?.putAll(sequenceOf(
                Rels.GET_SINGLE_BOARD to Link(Uri.forSingleBoardText(boardId)),
                Rels.GET_BOARD_MEMBERS to Link(Uri.forBoardMembers(boardId)),
                Rels.GET_MULTIPLE_BLACKBOARDS to Link(Uri.forAllBlackboards(boardId)),
                Rels.GET_SINGLE_USER to Link(Uri.forSingleUserText(board.creator.id)),
                Rels.SUBSCRIBE to Link(Uri.forBoardMembers(boardId))
        ))
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
                        href = Uri.forAllBoards(),
                        rel = "self" /*TODO: what rel should we use here?*/,
                        data = listOf(
                                Data(name = "page", value = "")
                        )
                )
        )
) {
    init {
        if (page < boardsPage.totalPages && boardsPage.nextPageable().isPaged)
            links?.add(CollectionLink("next", Uri.forAllBoards(page + 1)))
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
) : IHalObj