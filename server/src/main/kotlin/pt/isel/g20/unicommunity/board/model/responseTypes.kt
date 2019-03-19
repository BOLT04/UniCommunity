package pt.isel.g20.unicommunity.board.model

import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link

class BoardLinksResponse(selfLink: String) : HalObject(mapOf("self" to Link(selfLink))
)

class BoardResponse(val name: String, val description: String, selfLink: String) : HalObject(mapOf("self" to Link(selfLink))
)