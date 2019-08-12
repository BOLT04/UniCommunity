package isel.pt.unicommunity.model.webdto.rel_links

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.webdto.*
import isel.pt.unicommunity.model.webdto.clean.CollectionJson
import isel.pt.unicommunity.model.webdto.clean.NavLink







class CreateCommentLink (href: String): BodyNavLink<CommentOutputDto, CommentInputDto>(Rels.CREATE_COMMENT, href, CommentOutputDto::class.java, CommentInputDto::class.java)
class GetSingleCommentLink (href: String): NavLink<CommentInputDto>(Rels.GET_SINGLE_COMMENT, href, CommentInputDto::class.java)
class GetMultipleCommentsLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_COMMENTS, href, CollectionJson::class.java)
class EditCommentLink (href: String): BodyNavLink<CommentOutputDto, CommentInputDto>(Rels.EDIT_COMMENT, href, CommentOutputDto::class.java, CommentInputDto::class.java)
//todo class DeleteCommentLink (href: String): NavLink(Rels.DELETE_COMMENT, href)


class CommentOutputDto


class CommentInputDto(
    val boardName: String,
    val forumItemName: String,
    val authorName : String?,
    val content : String,
    val createdAt: String,
    val _links : CommentInputDtoLinkStruct,
    val _embedded : CommentInputDtoEmbeddedStruct?
)

class CommentInputDtoEmbeddedStruct {

}

class CommentInputDtoLinkStruct (
    val self : GetSingleCommentLink?,
    @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
    @JsonProperty(Rels.GET_SINGLE_BOARD) val getSingleBoardLink: GetSingleBoardLink?,
    @JsonProperty(Rels.GET_SINGLE_FORUM) val getSingleForumLink: GetSingleForumLink?,
    @JsonProperty(Rels.GET_SINGLE_FORUMITEM) val getSingleForumItemLink: GetSingleForumItemLink?,
    @JsonProperty(Rels.CREATE_COMMENT) val createCommentLink: CreateCommentLink?,
    @JsonProperty(Rels.GET_MULTIPLE_COMMENTS) val getMultipleCommentsLink: GetMultipleCommentsLink?,
    @JsonProperty(Rels.EDIT_COMMENT) val editCommentLink: EditCommentLink?,
    @JsonProperty(Rels.DELETE_COMMENT) val deleteCommentLink: CreateCommentLink?
)
