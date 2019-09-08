package isel.pt.unicommunity.model.links

import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.inputdto.BoardInputDto
import isel.pt.unicommunity.model.outputdto.BoardOutputDto


class GetMultipleBoardsLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_BOARDS, href, CollectionJson::class.java)
class GetSingleBoardLink (href: String): NavLink<BoardInputDto>(Rels.GET_SINGLE_BOARD, href, BoardInputDto::class.java)
class CreateBoardLink (href: String): BodyNavLink<BoardOutputDto, BoardInputDto>(Rels.CREATE_BOARD, href, BoardOutputDto::class.java, BoardInputDto::class.java)
class EditBoardLink (href: String): BodyNavLink<BoardOutputDto, BoardInputDto>(Rels.EDIT_BOARD, href, BoardOutputDto::class.java, BoardInputDto::class.java)













