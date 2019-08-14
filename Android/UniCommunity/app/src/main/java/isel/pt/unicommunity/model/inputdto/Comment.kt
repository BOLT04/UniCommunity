package isel.pt.unicommunity.model.inputdto

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.links.*
import isel.pt.unicommunity.model.Rels


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