package pt.isel.g20.unicommunity.blackboard.model

import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link

class BlackboardLinksResponse(selfLink: String) : HalObject(mapOf("self" to Link(selfLink))
)

class BlackboardResponse(
        val id: String,
        val name: String,
        val description: String,
        val notificationLevel: String,
        selfLink: String
    ) : HalObject(mapOf("self" to Link(selfLink))
)