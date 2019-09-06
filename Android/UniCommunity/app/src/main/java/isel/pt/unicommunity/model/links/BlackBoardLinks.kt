package isel.pt.unicommunity.model.links

import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.inputdto.BlackBoardInputDto
import isel.pt.unicommunity.model.outputdto.BlackBoardOutputDto

class GetMultipleBlackBoardsLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_BLACKBOARDS, href, CollectionJson::class.java)
class GetSingleBlackBoardLink (href: String): NavLink<BlackBoardInputDto>(Rels.GET_SINGLE_BLACKBOARD, href, BlackBoardInputDto::class.java)
class CreateBlackBoardLink (href: String): BodyNavLink<BlackBoardOutputDto, BlackBoardInputDto>(
    Rels.CREATE_BLACKBOARD, href, BlackBoardOutputDto::class.java, BlackBoardInputDto::class.java)
class EditBlackBoardLink (href: String): BodyNavLink<BlackBoardOutputDto, BlackBoardInputDto>(
    Rels.EDIT_BLACKBOARD, href, BlackBoardOutputDto::class.java, BlackBoardInputDto::class.java)
class DeleteBlackBoardLink (href: String)//todo : NavLink(Rels.DELETE_BLACKBOARD, href)







