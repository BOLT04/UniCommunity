package isel.pt.unicommunity.model.webdto

import com.fasterxml.jackson.annotation.JsonProperty

class NavDto (
    val links : NavLinkStruct
)

class NavLinkStruct(
    val self : SelfLink? = null,
    @JsonProperty("/rels/home") val home : HomeLink? = null,
    @JsonProperty("/rels/logout") val logout : LogoutLink? = null,
    @JsonProperty("/rels/userProfile") val userProfile : UserProfileLink? = null,
    @JsonProperty("/rels/allBoards") val allBoards : AllBoardsLink? = null,
    @JsonProperty("/rels/myBoards") val myBoards : MyBoardsLink? = null
)

