package isel.pt.unicommunity.model.webdto.rel_links

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.webdto.BodyNavLink
import isel.pt.unicommunity.model.webdto.Rels
import isel.pt.unicommunity.model.webdto.clean.NavLink






class NavigationLink(href: String): NavLink<NavigationInputDto>(Rels.NAVIGATION, href, NavigationInputDto::class.java)

class HomeLink (href: String): NavLink<HomeInputDto>(Rels.HOME, href, HomeInputDto::class.java)
class LoginLink (href: String): BodyNavLink<LoginInputDto, LoginOutputDto>(Rels.LOGIN, href, LoginInputDto::class.java, LoginOutputDto::class.java)
//todo class LogoutLink (href: String): NavLink(Rels.LOGOUT, href)
class MyBoardsLink (href: String): NavLink<MyBoardsInputDto>(Rels.MY_BOARDS, href, MyBoardsInputDto::class.java)
class UserProfileLink (href: String): NavLink<UserProfileInputDto>(Rels.USER_PROFILE, href, UserProfileInputDto::class.java)



class NavigationInputDto(
    val _links : NavigationInputDtoLinkStruct
)

class NavigationInputDtoLinkStruct (
    val self : NavigationLink?,
    @JsonProperty(Rels.HOME) val home : HomeLink?,
    @JsonProperty(Rels.USER_PROFILE) val userProfile : UserProfileLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BOARDS) val allBoards : GetMultipleBoardsLink?
    //@JsonProperty(Rels.get) val myBoards : MyBoardsLink? = null
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


class MyBoardsInputDto //todo
class UserProfileInputDto //todo