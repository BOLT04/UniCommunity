package isel.pt.unicommunity.model.links

import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.Rels


class TemplateInputDto
class TemplateOutputDto

class CreateTemplateLink (href: String): BodyNavLink<TemplateOutputDto, TemplateInputDto>(
    Rels.CREATE_TEMPLATE, href, TemplateOutputDto::class.java, TemplateInputDto::class.java)
class GetSingleTemplateLink (href: String): NavLink<TemplateInputDto>(Rels.GET_SINGLE_TEMPLATE, href, TemplateInputDto::class.java)
class GetMultipleTemplatesLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_TEMPLATES, href, CollectionJson::class.java)
class EditTemplateLink (href: String): BodyNavLink<TemplateOutputDto, TemplateInputDto>(Rels.EDIT_TEMPLATE, href, TemplateOutputDto::class.java, TemplateInputDto::class.java)
//todo class DeleteTemplateLink (href: String): NavLink(Rels.DELETE_TEMPLATE, href)


