package isel.pt.unicommunity.model.inputdto

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.links.*
import isel.pt.unicommunity.model.Rels


class BlackBoardItemInputDto(
    val name: String,
    val content : String?,
    val createdAt : String,

    val _links : LinkStruct
) {

    class LinkStruct(
        val self: GetSingleBlackBoardItemLink?,
        @JsonProperty(Rels.NAVIGATION) val nav: NavigationLink?,
        @JsonProperty(Rels.GET_SINGLE_BLACKBOARD) val getSingleBlackBoardLink: GetSingleBlackBoardLink?,
        @JsonProperty(Rels.GET_SINGLE_BOARD) val getSingleBoardLink: GetSingleBoardLink?,
        @JsonProperty(Rels.CREATE_BLACKBOARDITEM) val createBlackBoardItemLink: CreateBlackBoardItemLink?,
        @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDITEMS) val getMultipleBlackBoardItemsLink: GetMultipleBlackBoardItemsLink?,
        @JsonProperty(Rels.EDIT_BLACKBOARDITEM) val editBlackBoardItemLink: EditBlackBoardItemLink?//,
        //todo @JsonProperty(Rels.DELETE_BLACKBOARDITEM) val deleteBlackBoardItemLink: DeleteBlackBoardItemLink?
    )
}