package isel.pt.unicommunity.model.links

import isel.pt.unicommunity.model.BodyNavLink
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.NavLink
import isel.pt.unicommunity.model.inputdto.BlackBoardItemInputDto
import isel.pt.unicommunity.model.outputdto.BlackBoardItemOutputDto


class GetMultipleBlackBoardItemsLink(
    href: String
) : NavLink<CollectionJson>(
    Rels.GET_MULTIPLE_BLACKBOARDITEMS,
    href,
    CollectionJson::class.java
)

class GetSingleBlackBoardItemLink(href: String) : NavLink<BlackBoardItemInputDto>(
    Rels.GET_SINGLE_BLACKBOARDITEM,
    href,
    BlackBoardItemInputDto::class.java
)

class CreateBlackBoardItemLink(
    href: String
) : BodyNavLink<BlackBoardItemOutputDto, BlackBoardItemInputDto>(
    Rels.CREATE_BLACKBOARDITEM,
    href,
    BlackBoardItemOutputDto::class.java,
    BlackBoardItemInputDto::class.java
)

class EditBlackBoardItemLink(
    href: String
) : BodyNavLink<BlackBoardItemOutputDto, BlackBoardItemInputDto>(
    Rels.EDIT_BLACKBOARDITEM,
    href,
    BlackBoardItemOutputDto::class.java,
    BlackBoardItemInputDto::class.java
)

