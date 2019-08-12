package isel.pt.unicommunity.model.webmapper



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

/*class BlackBoardMapper : IMapper<BlackBoardDto, BlackBoard>{


    override fun dtoToModel(dto: BlackBoardDto): BlackBoard {
        val smallBBItems = dto.items.map {
            SmallBlackBoardItem(it.APP_NAME, it.text)
        }

        val _links = dto._links

        val boardRet = parseRel(_links.board)
        val navRet = parseRel(_links.nav)
        val selfRet = parseRel(_links.nav)

        return BlackBoard(
            dto.APP_NAME,
            smallBBItems,

            self = selfRet,
            board = boardRet,
            blackboards = null, //todo nao existe?!
            nav = navRet

        )

    }


}

*/
