package isel.pt.unicommunity.model.webdto

import com.fasterxml.jackson.annotation.JsonProperty

class BoardDto(
    val name : String,
    val description: String? = null,
    val _links : BoardLinkStruct
)
class BoardLinkStruct(
    val self : SelfLink? = null,
    @JsonProperty("/rels/nav")val nav : NavigationLink? = null,
    @JsonProperty("/rels/blackboards")val blackBoards: BlackBoardsLink? = null,
    @JsonProperty("/rels/forum")val forum: ForumLink? = null
)

