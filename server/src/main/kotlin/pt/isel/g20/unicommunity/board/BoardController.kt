package pt.isel.g20.unicommunity.board

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.service.IBoardService
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.hateoas.Uri
import pt.isel.g20.unicommunity.hateoas.Uri.BOARDS_ROUTE
import pt.isel.g20.unicommunity.hateoas.Uri.SINGLE_BOARD_ROUTE
import java.util.concurrent.TimeUnit
import org.springframework.security.core.context.SecurityContextHolder
import pt.isel.g20.unicommunity.board.model.*
import pt.isel.g20.unicommunity.hateoas.Uri.BOARD_MEMBERS
import pt.isel.g20.unicommunity.user.model.User

@PreAuthorize("hasRole('TEACHER')")
@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class BoardController(private val service: IBoardService) {

    @GetMapping(path = [BOARDS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllBoards() =
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


    @PostMapping(path = [BOARD_MEMBERS], produces = ["application/hal+json"])
    fun addUserToBoard(@PathVariable boardId: Long): ResponseEntity<SingleBoardResponse>{
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as User
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

    @PostMapping(path = [SINGLE_BOARD_ROUTE], produces = ["application/hal+json"])
    fun removeUserFromBoard(@PathVariable boardId: Long): ResponseEntity<SingleBoardResponse>{
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as User
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