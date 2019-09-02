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
//todo class LogoutLink (href: String): NavLink(Rels.LOGOUT, href)
class MyBoardsLink (href: String): NavLink<CollectionJson>(Rels.MY_BOARDS, href, CollectionJson::class.java)
class UserProfileLink (href: String): NavLink<UserProfileInputDto>(Rels.USER_PROFILE, href, UserProfileInputDto::class.java)

class SubscribeLink (href: String) : BodyNavLink<SubscribeDto, BoardInputDto>(Rels.SUBSCRIBE, href, SubscribeDto::class.java, BoardInputDto::class.java)

class SubscribeDto {

}


class NavigationInputDto(
    val _links : NavigationInputDtoLinkStruct
)

class NavigationInputDtoLinkStruct (
    val self : NavigationLink?,
    @JsonProperty(Rels.HOME) val home : HomeLink?,
    @JsonProperty(Rels.USER_PROFILE) val userProfile : UserProfileLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BOARDS) val allBoards : GetMultipleBoardsLink?,
    @JsonProperty(Rels.MY_BOARDS) val myBoards : MyBoardsLink? = null
)


class HomeInputDto //todo

class LoginInputDto(
    val email : String,
    val name: String,
    val _links: LoginInputDtoLinkStructure
)

class LoginInputDtoLinkStructure(
    val self : LoginLink?,
    @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BOARDS) val getMultipleBoardsLink: GetMultipleBoardsLink?
)


class LoginOutputDto(val email: String, val password:String)



class UserProfileInputDto //todo