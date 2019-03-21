package pt.isel.g20.unicommunity.user.model

import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link

class UserLinksResponse(selfLink: String) : HalObject(mapOf("self" to Link(selfLink))
)

class UserResponse(val id: String, val name: String, val email: String, selfLink: String) : HalObject(mapOf("self" to Link(selfLink))
)