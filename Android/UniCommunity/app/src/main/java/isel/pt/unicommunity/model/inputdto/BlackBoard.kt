package isel.pt.unicommunity.model.inputdto

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.links.*
import isel.pt.unicommunity.model.Rels

class BlackBoardInputDto(
    val name: String,
    val description: String?,
    val notificationLevel: String?,
    val _links : LinkStruct,
    val _embedded : EmbeddedStruct?
){

    class LinkStruct(
        val self : GetSingleBlackBoardLink?,
        @JsonProperty(Rels.GET_SINGLE_BOARD) val getSingleBoardLink: GetSingleBoardLink?,
        @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDS) val getMultipleBlackBoardsLink: GetMultipleBlackBoardsLink?,
        @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDITEMS) val getMultipleBlackBoardItemsLink: GetMultipleBlackBoardItemsLink?,
        @JsonProperty(Rels.CREATE_BLACKBOARDITEM) val createBlackBoardItemLink: CreateBlackBoardItemLink?,
        @JsonProperty(Rels.EDIT_BLACKBOARD) val editBlackBoardLink: EditBlackBoardLink?

    )

    class EmbeddedStruct(
        @JsonProperty(Rels.GET_SINGLE_BOARD) val board : PartialBoard?,
        @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDITEMS) val blackboardItems: Array<PartialBlackBoardItem>?
    ){
        class PartialBoard(
            val name : String?,
            val _links: PartialBoardLinkStruct
        ){
            class PartialBoardLinkStruct(
                val self : GetSingleBoardLink
            )
        }

        class PartialBlackBoardItem(
            val name : String?,
            val author : String?,
            val date : String?,
            val _links : PartialBlackBoardItemLinkStruct
        ){
            class PartialBlackBoardItemLinkStruct(
                val self: GetSingleBlackBoardItemLink
            )
        }
    }
}