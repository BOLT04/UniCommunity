package isel.pt.unicommunity.model.inputdto

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.links.*
import isel.pt.unicommunity.model.Rels

class ForumItemInputDto(
    val name : String,
    val content : String,
    val createdAt : String,
    val anonymousPost : Boolean,
    val _links : ForumItemInputDtoLinkStruct,
    val _embedded : ForumItemInputDtoEmbeddedStruct?
){


    class ForumItemInputDtoLinkStruct (

        val self : GetSingleForumItemLink?,
        @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
        @JsonProperty(Rels.GET_SINGLE_BOARD) val getSingleBoardLink: GetSingleBoardLink?,
        @JsonProperty(Rels.GET_SINGLE_FORUM) val getSingleForumLink: GetSingleForumLink?,
        @JsonProperty(Rels.CREATE_FORUMITEM) val createForumItemLink: CreateForumItemLink?,
        @JsonProperty(Rels.GET_MULTIPLE_FORUMITEMS) val getMultipleForumItemsLink: GetMultipleForumItemsLink?,
        @JsonProperty(Rels.EDIT_FORUMITEM) val editForumItemLink: EditForumItemLink?,
        @JsonProperty(Rels.DELETE_FORUMITEM) val deleteForumItemLink: CreateForumItemLink?,
        @JsonProperty(Rels.GET_MULTIPLE_COMMENTS) val getMultipleCommentsLink: GetMultipleCommentsLink?,
        @JsonProperty(Rels.CREATE_COMMENT) val createCommentLink: CreateCommentLink?,
        @JsonProperty(Rels.GET_SINGLE_USER) val getSingleUserLink: GetSingleUserLink?
    )

    class ForumItemInputDtoEmbeddedStruct (
        @JsonProperty(Rels.GET_SINGLE_USER) val user : UserEmbedded?
    ){
        class UserEmbedded(
            val name : String?,
            val email: String?,
            val links: LinkStruct?
        ){
            class LinkStruct(
                val self: GetSingleUserLink?
            )
        }
    }
}
