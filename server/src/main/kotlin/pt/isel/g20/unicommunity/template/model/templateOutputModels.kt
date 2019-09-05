package pt.isel.g20.unicommunity.template.model

import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.CollectionLink
import pt.isel.g20.unicommunity.hateoas.Item
import pt.isel.g20.unicommunity.hateoas.JsonCollection

class SingleTemplateResponse(template: Template) {
    val name : String = template.name
    val id : Long = template.id
    val hasForum : Boolean = template.hasForum
    val blackboardNames : List<String> = template.blackboardNames.split(",")
}

class MultipleTemplatesResponse(
        templates : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllTemplates(),
        links = mutableListOf(
                CollectionLink("self",Uri.forAllTemplates())
        ),
        items = templates
)