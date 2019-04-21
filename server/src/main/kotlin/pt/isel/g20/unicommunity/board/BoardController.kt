package pt.isel.g20.unicommunity.board

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.model.BoardDto
import pt.isel.g20.unicommunity.board.model.MultipleBoardsResponse
import pt.isel.g20.unicommunity.board.model.SingleBoardResponse
import pt.isel.g20.unicommunity.board.service.IBoardService
import pt.isel.g20.unicommunity.hateoas.Uri
import pt.isel.g20.unicommunity.hateoas.Uri.BOARDS_ROUTE
import pt.isel.g20.unicommunity.hateoas.Uri.SINGLE_BOARD_ROUTE
import java.util.concurrent.TimeUnit

@RestController
class BoardController(private val service: IBoardService) {

    @GetMapping(path = [BOARDS_ROUTE])
    fun getAllBoards() =
            service.getAllBoards().let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(MultipleBoardsResponse(it))
            }

    @GetMapping(path = [SINGLE_BOARD_ROUTE])
    fun getBoardById(@PathVariable boardId: Long) =
            service.getBoardById(boardId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBoardResponse(it))
            }

    @PostMapping(path = [BOARDS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBoard(@RequestBody boardDto: BoardDto) =
            service.createBoard(boardDto.name, boardDto.description).let {
                ResponseEntity
                        .created(Uri.forSingleBoard(it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBoardResponse(it))
            }

    @PutMapping(path = [SINGLE_BOARD_ROUTE])
    fun editBoard(@PathVariable boardId: Long, @RequestBody boardDto: BoardDto) =
            service.editBoard(boardId, boardDto.name, boardDto.description).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBoardResponse(it))
            }


    @DeleteMapping(path = [SINGLE_BOARD_ROUTE])
    fun deleteBoard(@PathVariable boardId: Long) =
            service.deleteBoard(boardId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBoardResponse(it))
            }


    @ExceptionHandler
    fun handleNotFoundBoardException(e: NotFoundBoardException) =
            ResponseEntity
                    .notFound()
                    .build<String>()
}