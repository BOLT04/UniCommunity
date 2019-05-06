package isel.pt.unicommunity.model.webdto

import com.fasterxml.jackson.annotation.JsonProperty

class BlackBoardDto(

    val name: String,
    val items : Array<SmallBlackBoardItemDto>,
    val _links : BlackBoardLinkStruct


)

class BlackBoardLinkStruct(
    val self : SelfLink? = null,
    @JsonProperty("/rels/nav")val nav : NavigationLink? = null,
    @JsonProperty("/rels/board")val board: BoardLink? = null
)




class SmallBlackBoardItemDto(
    val name : String,
    val text : String
)
