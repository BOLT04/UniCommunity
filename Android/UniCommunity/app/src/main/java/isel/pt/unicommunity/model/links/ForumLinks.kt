package isel.pt.unicommunity.model.links

import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.inputdto.ForumInputDto
import isel.pt.unicommunity.model.outputdto.ForumOutputDto


class CreateForumLink (href: String): BodyNavLink<ForumOutputDto, ForumInputDto>(Rels.CREATE_FORUM, href, ForumOutputDto::class.java, ForumInputDto::class.java)
class GetSingleForumLink (href: String): NavLink<ForumInputDto>(Rels.GET_SINGLE_FORUM, href, ForumInputDto::class.java)
class EditForumLink (href: String): BodyNavLink<ForumOutputDto, ForumInputDto>(Rels.EDIT_FORUM, href, ForumOutputDto::class.java, ForumInputDto::class.java)
//todo class DeleteForumLink (href: String): NavLink(Rels.DELETE_FORUM, href)







