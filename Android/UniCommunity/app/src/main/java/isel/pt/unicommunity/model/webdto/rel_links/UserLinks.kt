package isel.pt.unicommunity.model.webdto.rel_links

import isel.pt.unicommunity.model.webdto.BodyNavLink
import isel.pt.unicommunity.model.webdto.Rels
import isel.pt.unicommunity.model.webdto.clean.CollectionJson
import isel.pt.unicommunity.model.webdto.clean.NavLink


class UserInputDto
class UserOutputDto


class CreateUserLink (href: String): BodyNavLink<UserOutputDto, UserInputDto>(Rels.CREATE_USER, href, UserOutputDto::class.java, UserInputDto::class.java)
class GetSingleUserLink (href: String): NavLink<UserInputDto>(Rels.GET_SINGLE_USER, href, UserInputDto::class.java)
class GetMultipleUsersLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_USERS, href, CollectionJson::class.java)
class EditUserLink (href: String): BodyNavLink<UserOutputDto, UserInputDto>(Rels.EDIT_USER, href, UserOutputDto::class.java, UserInputDto::class.java)
//todo class DeleteUserLink (href: String): NavLink(Rels.DELETE_USER, href)
