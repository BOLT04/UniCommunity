package isel.pt.unicommunity.model.links

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.inputdto.BoardInputDto


class NavigationLink(href: String): NavLink<NavigationInputDto>(Rels.NAVIGATION, href, NavigationInputDto::class.java)

class HomeLink (href: String): NavLink<HomeInputDto>(Rels.HOME, href, HomeInputDto::class.java)
class LoginLink (href: String): BodyNavLink<LoginOutputDto, LoginInputDto>(Rels.LOGIN, href, LoginOutputDto::class.java, LoginInputDto::class.java)
class MyBoardsLink (href: String): NavLink<CollectionJson>(Rels.MY_BOARDS, href, CollectionJson::class.java)




class BlackBoardSettingsLink (href: String) : NavLink<CollectionJson>(Rels.GET_USER_BLACKBOARDS_SETTINGS, href, CollectionJson::class.java)



class NavigationInputDto(
    val _links : NavigationInputDtoLinkStruct
)

class NavigationInputDtoLinkStruct (
    val self : NavigationLink?,
    @JsonProperty(Rels.HOME) val home : HomeLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BOARDS) val allBoards : GetMultipleBoardsLink?,
    @JsonProperty(Rels.MY_BOARDS) val myBoards : MyBoardsLink?,
    @JsonProperty(Rels.GET_MULTIPLE_REPORTS) val getMultipleReportsLink: GetMultipleReportsLink?
)


class HomeInputDto(
    val _links : LinkStruct
){
    class LinkStruct(
        val self: HomeLink?,
        @JsonProperty(Rels.LOGIN) val login : LoginLink?
    )
}

class LoginInputDto(
    val email : String,
    val name: String,
    val _links: LoginInputDtoLinkStructure
)

class LoginInputDtoLinkStructure(
    val self : LoginLink?,
    @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BOARDS) val getMultipleBoardsLink: GetMultipleBoardsLink?,
    @JsonProperty(Rels.GET_USER_BLACKBOARDS_SETTINGS) val getBlackBoardSettingsLink: BlackBoardSettingsLink?

)


class LoginOutputDto(val email: String, val password:String)



