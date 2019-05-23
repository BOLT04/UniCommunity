package pt.isel.g20.unicommunity.template.model

import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*

fun Template.toItemRepr() = Item(
        href = Uri.forSingleTemplateText(id),
        data =listOf(
                Data(name= "name", value= this.name),
                Data(name= "id", value= this.id.toString()),
                Data(name= "hasForum", value= this.hasForum.toString()),
                Data(name= "blackTemplateNames", value= this.blackboardNames)
        ),
        links = listOf(
                CollectionLink(
                        rel= "self",
                        href= Uri.forSingleTemplateText(id)
                ),
                CollectionLink(
                        rel= Rels.EDIT_TEMPLATE,
                        href= Uri.forSingleTemplateText(this.id)
                ),
                CollectionLink(
                        rel= Rels.DELETE_TEMPLATE,
                        href= Uri.forSingleTemplateText(this.id)
                ),
                CollectionLink(
                        rel= Rels.GET_MULTIPLE_TEMPLATES,
                        href= Uri.forAllTemplates()
                )
        )
)