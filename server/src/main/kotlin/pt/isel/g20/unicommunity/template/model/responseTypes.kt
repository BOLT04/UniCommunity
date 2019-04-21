package pt.isel.g20.unicommunity.template.model

import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link

class TemplateLinksResponse(selfLink: String) : HalObject(mapOf("self" to Link(selfLink))
)

class TemplateResponse(val name: String, val modules: Array<String>, selfLink: String) : HalObject(mapOf("self" to Link(selfLink))
)