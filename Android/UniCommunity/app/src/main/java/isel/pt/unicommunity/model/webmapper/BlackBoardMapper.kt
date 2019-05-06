package isel.pt.unicommunity.model.webmapper

import isel.pt.unicommunity.model.webdto.BlackBoardDto
import isel.pt.unicommunity.model.webdto.NavLink


class BlackBoard(
    val name: String,

    val smallBlackBoardItem: List<SmallBlackBoardItem>,

    val self : Ret?,
    val nav : Ret?,
    val blackboards: Ret?,
    val board : Ret?
)

class SmallBlackBoardItem(val name:String, val text: String
    //todo esta formatação esta mal, val goto: Ret
)

class BlackBoardMapper : IMapper<BlackBoardDto, BlackBoard>{


    override fun dtoToModel(dto: BlackBoardDto): BlackBoard {
        val smallBBItems = dto.items.map {
            SmallBlackBoardItem(it.name, it.text)
        }

        val _links = dto._links

        val boardRet = parseRel(_links.board)
        val navRet = parseRel(_links.nav)
        val selfRet = parseRel(_links.nav)

        return BlackBoard(
            dto.name,
            smallBBItems,

            self = selfRet,
            board = boardRet,
            blackboards = null, //todo nao existe?!
            nav = navRet

        )

    }


}


