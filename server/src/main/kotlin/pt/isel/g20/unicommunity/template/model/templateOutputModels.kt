package pt.isel.g20.unicommunity.template.model

import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

class SingleTemplateResponse(template: Template) {
    val name : String = template.name
    val id : Long = template.id
    val hasForum : Boolean = template.hasForum
    val blackboardNames : List<String> = template.blackboardNames.split(",")
}


class MultipleTemplatesResponse(
        templates : Iterable<Template>
){
    val items = templates.map { Item( Uri.forSingleTemplateText(it.id)) } // TODO: mapping has to go outside
}