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
    private val singleProjectTemplate = UriTemplate(SINGLE_USER_ROUTE)

    //Getters
    fun forAllUsers() =
            URI(USERS_ROUTE)

    fun forSingleUser(userId: Long) =
            singleProjectTemplate.expand(userId)
}