package isel.pt.unicommunity.model.webdto.old.blackboard

import com.google.gson.annotations.SerializedName
import isel.pt.unicommunity.model.webdto.*

class BlackBoardInputDto(

    val name: String,
    val items : Array<LocalSmallBlackBoardItemDto>,
    val _links : BlackBoardLinkStruct


)

class BlackBoardLinkStruct(
    /*val self : SelfLink?,
    @SerializedName(Rels.NAVIGATION) val nav : NavigationLink?,
    @SerializedName(Rels.GET_SINGLE_BOARD) val getSingleBoardLink: GetSingleBoardLink?,
    @SerializedName(Rels.CREATE_BOARD) val createBoardLink: CreateBoardLink?*/
)




class LocalSmallBlackBoardItemDto(
    val name : String,
    val text : String
)
