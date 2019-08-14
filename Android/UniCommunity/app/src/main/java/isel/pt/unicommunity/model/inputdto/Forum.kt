package isel.pt.unicommunity.model.inputdto

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.links.*
import isel.pt.unicommunity.model.Rels

class ForumInputDto(
    val id : String,
    val _links : ForumInputDtoLinkStruct,
    val _embedded : ForumInputDtoEmbeddedStruct
)

class ForumInputDtoLinkStruct(
    val self : GetSingleForumLink?,
    @JsonProperty(Rels.GET_MULTIPLE_FORUMITEMS) val getMultipleForumItemsLink: GetMultipleForumItemsLink?,
    @JsonProperty(Rels.CREATE_FORUMITEM) val createForumItemLink: CreateForumItemLink?
)

class ForumInputDtoEmbeddedStruct(
    @JsonProperty(Rels.GET_SINGLE_BOARD) val embeddedBoard : PartialBoardInputDto?,
    @JsonProperty(Rels.GET_MULTIPLE_FORUMITEMS) val embeddedForumItems : Array<PartialForumItemDto>?
){
    class PartialBoardInputDto(
        val name : String?,
        val _links: PartialBoardLinkStruct?
    ) {
        class PartialBoardLinkStruct(val self: GetSingleBoardLink?)
    }

    class PartialForumItemDto(
        val name: String?,
        val author: String?,
        val _links: PartialForumItemLinkStruct?
    ) {
        class PartialForumItemLinkStruct(val self: GetSingleForumItemLink?)
    }
}