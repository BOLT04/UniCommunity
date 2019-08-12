package isel.pt.unicommunity.model.webmapper

/*import isel.pt.unicommunity.model.webdto.old.board.BoardDto



class BoardMapper : IMapper<BoardDto,Board> {

    override fun dtoToModel(dto: BoardDto): Board {

        val links = dto._links

        val self = parseRel(links.self)
        //val nav = parseRel(links.nav)
       // val blackBoards = parseRel(links.blackBoards)
        //val forum = parseRel(links.forum)

        return Board(
            dto.APP_NAME,
            dto.description,

            self = self
            //nav = nav,
           // blackboards = blackBoards,
          //  forum = forum
        )
    }

}*/