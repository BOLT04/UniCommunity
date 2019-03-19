package pt.isel.g20.unicommunity.board

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import pt.isel.g20.unicommunity.board.model.BoardDto
import pt.isel.g20.unicommunity.board.model.BoardLinksResponse
import pt.isel.g20.unicommunity.board.model.BoardResponse
import pt.isel.g20.unicommunity.board.service.IBoardService

private const val LIST_BOARDS_ROUTE = "/api/boards"
private const val GET_BOARD_ROUTE = "/api/boards/{board-name}"

@RestController
class BoardController(private val service: IBoardService) {

    @GetMapping(path = [LIST_BOARDS_ROUTE])
    fun getAllBoards() = service.getAllBoards()

    @PostMapping(path = [LIST_BOARDS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBoard(@RequestBody boardDto: BoardDto): BoardLinksResponse {
        service.createBoard(boardDto.toModel())

        val boardName = boardDto.name.replace(' ', '+')
        return BoardLinksResponse(
                "$LIST_BOARDS_ROUTE/$boardName"
        )
    }

    @RequestMapping("/api/boards/{board-name}", method = [RequestMethod.GET])
    fun getBoardByName(@PathVariable(value="board-name") name: String) : BoardResponse {
        val board = service.getBoardByName(name)
                ?: throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A board with the given name couldn't be found.")

        return BoardResponse(board.name, board.description,
                "$LIST_BOARDS_ROUTE/${board.name}"
        )
    }
}