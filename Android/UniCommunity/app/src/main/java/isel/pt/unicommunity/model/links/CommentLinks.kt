package isel.pt.unicommunity.model.links

import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.inputdto.CommentInputDto
import isel.pt.unicommunity.model.outputdto.CommentOutputDto


class CreateCommentLink (href: String): BodyNavLink<CommentOutputDto, CommentInputDto>(Rels.CREATE_COMMENT, href, CommentOutputDto::class.java, CommentInputDto::class.java)
class GetSingleCommentLink (href: String): NavLink<CommentInputDto>(Rels.GET_SINGLE_COMMENT, href, CommentInputDto::class.java)
class GetMultipleCommentsLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_COMMENTS, href, CollectionJson::class.java)
class EditCommentLink (href: String): BodyNavLink<CommentOutputDto, CommentInputDto>(Rels.EDIT_COMMENT, href, CommentOutputDto::class.java, CommentInputDto::class.java)





