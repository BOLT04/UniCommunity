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
}