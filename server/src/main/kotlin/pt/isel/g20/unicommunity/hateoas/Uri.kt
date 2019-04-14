package pt.isel.g20.unicommunity.hateoas

import org.springframework.web.util.UriTemplate
import java.net.URI

object Uri {
    const val LINK_RELATIONS_ROUTE = "/rels"


//USERS

    //Paths
    const val USERS_ROUTE = "/users"
    const val SINGLE_USER_ROUTE = "$USERS_ROUTE/{userId}"

    //Templates
    private val singleUserTemplate = UriTemplate(SINGLE_USER_ROUTE)

    //Getters
    fun forAllUsers() =
            URI(USERS_ROUTE)

    fun forSingleUser(userId: Long) =
            singleUserTemplate.expand(userId)


//BOARDS

    //Paths
    const val BOARDS_ROUTE = "/boards"
    const val SINGLE_BOARD_ROUTE = "$BOARDS_ROUTE/{boardId}"

    //Templates
    private val singleBoardTemplate = UriTemplate(SINGLE_BOARD_ROUTE)

    //Getters
    fun forAllBoards() =
            URI(BOARDS_ROUTE)

    fun forSingleBoard(boardId: Long) =
            singleBoardTemplate.expand(boardId)


//BLACKBOARDS

    //Paths
    const val BLACKBOARDS_ROUTE = "$SINGLE_BOARD_ROUTE/blackboards"
    const val SINGLE_BLACKBOARD_ROUTE = "$BLACKBOARDS_ROUTE/{bbId}"

    //Templates
    private val singleBlackboardTemplate = UriTemplate(SINGLE_BLACKBOARD_ROUTE)
    private val multipleBlackboardTemplate = UriTemplate(BLACKBOARDS_ROUTE)

    //Getters
    fun forAllBlackboards(boardId: Long) =
            multipleBlackboardTemplate.expand(boardId)

    fun forSingleBlackboard(boardId: Long, bbId: Long) =
            singleBlackboardTemplate.expand(boardId, bbId)


//BLACKBOARDITEMS

    //Paths
    const val BLACKBOARDITEMS_ROUTE = "$SINGLE_BLACKBOARD_ROUTE/submissions"
    const val SINGLE_BLACKBOARDITEM_ROUTE = "$BLACKBOARDITEMS_ROUTE/{itemId}"

    //Templates
    private val singleBlackboardItemTemplate = UriTemplate(SINGLE_BLACKBOARDITEM_ROUTE)
    private val multipleBlackboardItemTemplate = UriTemplate(BLACKBOARDITEMS_ROUTE)

    //Getters
    fun forAllBlackboardItems(boardId: Long, bbId: Long) =
            multipleBlackboardItemTemplate.expand(boardId, bbId)

    fun forSingleBlackboardItem(boardId: Long, bbId: Long, itemId: Long) =
            singleBlackboardItemTemplate.expand(boardId, bbId, itemId)
}