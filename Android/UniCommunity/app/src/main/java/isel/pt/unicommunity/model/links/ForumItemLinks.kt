package isel.pt.unicommunity.model.links

import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.inputdto.ForumItemInputDto
import isel.pt.unicommunity.model.outputdto.ForumItemOutputDto


class CreateForumItemLink (href: String): BodyNavLink<ForumItemOutputDto, ForumItemInputDto>(
    Rels.CREATE_FORUMITEM, href, ForumItemOutputDto::class.java, ForumItemInputDto::class.java)
class GetSingleForumItemLink (href: String): NavLink<ForumItemInputDto>(Rels.GET_SINGLE_FORUMITEM, href, ForumItemInputDto::class.java)
class GetMultipleForumItemsLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_FORUMITEMS, href, CollectionJson::class.java)
class EditForumItemLink (href: String): BodyNavLink<ForumItemOutputDto, ForumItemInputDto>(
    Rels.EDIT_FORUMITEM, href, ForumItemOutputDto::class.java, ForumItemInputDto::class.java)








