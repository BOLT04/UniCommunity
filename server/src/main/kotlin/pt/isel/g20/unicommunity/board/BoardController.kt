package pt.isel.g20.unicommunity.board

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.service.IBoardService
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.Uri.BOARDS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_BOARD_ROUTE
import java.util.concurrent.TimeUnit
import pt.isel.g20.unicommunity.board.model.*
import pt.isel.g20.unicommunity.common.Uri.BOARD_MEMBERS
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.user.model.User

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class BoardController(private val service: IBoardService) {

    @AuthorizationRequired
    @GetMapping(path = [BOARDS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllBoards(@SessionAttribute("user") user: User) =
            service.getAllBoards().map(Board::toItemRepr).let {
                val response = CollectionObject(MultipleBoardsResponse(it))

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @AuthorizationRequired
    @GetMapping(path = [SINGLE_BOARD_ROUTE], produces = ["application/hal+json"])
    fun getBoardById(@PathVariable boardId: Long) =
            service.getBoardById(boardId).let {
                val response = SingleBoardResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @AuthorizationRequired
    @PostMapping(path = [BOARDS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBoard(@RequestBody boardDto: BoardDto) =
            service.createBoard(
                    boardDto.name,
                    boardDto.templateId,
                    boardDto.description,
                    boardDto.blackboardNames,
                    boardDto.hasForum).let {
                val response = SingleBoardResponse(it)

                ResponseEntity
                        .created(Uri.forSingleBoardUri(it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_BOARD_ROUTE], produces = ["application/hal+json"])
    fun editBoard(@PathVariable boardId: Long, @RequestBody boardDto: BoardDto) =
            service.editBoard(boardId, boardDto.name, boardDto.description).let {
                val response = SingleBoardResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }


    @AuthorizationRequired
    @DeleteMapping(path = [BOARD_MEMBERS], produces = ["application/hal+json"])
    fun deleteBoard(@PathVariable boardId: Long) =
            service.deleteBoard(boardId).let {
                val response = SingleBoardResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @AuthorizationRequired
    @PostMapping(path = [BOARD_MEMBERS], produces = ["application/hal+json"])
    fun addUserToBoard(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User): ResponseEntity<SingleBoardResponse>{
        return service.addUserToBoard(boardId, user.id).let {
            val response = SingleBoardResponse(it)

            ResponseEntity
                    .ok()
                    .cacheControl(
                            CacheControl
                                    .maxAge(1, TimeUnit.HOURS)
                                    .cachePrivate())
                    .eTag(response.hashCode().toString())
                    .body(response)
        }
    }

    @AuthorizationRequired
    @PostMapping(path = [SINGLE_BOARD_ROUTE], produces = ["application/hal+json"])
    fun removeUserFromBoard(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User): ResponseEntity<SingleBoardResponse>{
        return service.removeUserFromBoard(boardId, user.id).let {
            val response = SingleBoardResponse(it)

            ResponseEntity
                    .ok()
                    .cacheControl(
                            CacheControl
                                    .maxAge(1, TimeUnit.HOURS)
                                    .cachePrivate())
                    .eTag(response.hashCode().toString())
                    .body(response)
        }
    }

    @ExceptionHandler
    fun handleNotFoundBoardException(e: NotFoundBoardException) =
            ResponseEntity
                    .notFound()
                    .build<String>()
}