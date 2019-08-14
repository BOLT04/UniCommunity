package isel.pt.unicommunity.model.inputdto

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.links.*
import isel.pt.unicommunity.model.Rels


class BoardInputDto(
    val name : String,
    val description: String?,
    val _links : LinkStruct,
    val _embedded : EmbeddedStruct?
) {

    class LinkStruct(
        val self : GetSingleBoardLink?,
        @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
        @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDS) val getMultipleBlackBoardsLink: GetMultipleBoardsLink?,
        @JsonProperty(Rels.CREATE_BOARD) val createBoardLink: CreateBoardLink?,
        @JsonProperty(Rels.GET_MULTIPLE_BOARDS) val getMultipleBoardsLink: GetMultipleBoardsLink?,
        @JsonProperty(Rels.EDIT_BOARD) val editBoardLink: EditBoardLink?,
        //@JsonProperty(Rels.DELETE_BOARD) val deleteBoardLink: DeleteBoardLink?, TODO

        @JsonProperty(Rels.GET_MULTIPLE_FORUMITEMS) val getMultipleForumItemsLink: GetMultipleForumItemsLink?,
        @JsonProperty(Rels.GET_SINGLE_FORUM) val getSingleForumLink: GetSingleForumLink?
    )

    class EmbeddedStruct(
        @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDS) val smallBlackBoardBoards: Array<PartialBlackBoardInputDto>?
    ) {

        class PartialBlackBoardInputDto(
            val name: String?,
            val _links: PartialBlackBoardLinkStruct
        ){
            class PartialBlackBoardLinkStruct(
                val self: GetSingleBlackBoardLink?
            )
        }
    }


}