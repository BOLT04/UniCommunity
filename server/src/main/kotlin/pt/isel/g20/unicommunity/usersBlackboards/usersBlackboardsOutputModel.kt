package pt.isel.g20.unicommunity.usersBlackboards

import pt.isel.g20.unicommunity.hateoas.IHalObj
import pt.isel.g20.unicommunity.hateoas.Link

class PartialUsersBlackboardsObject(
        val userId: Long,
        val boardId: Long,
        val blackboardId: Long,
        val userEmail: String,
        val boardName: String,
        val blackboardName: String,
        val notificationLevel: String,
        val _links: Map<String, Link>
) : IHalObj