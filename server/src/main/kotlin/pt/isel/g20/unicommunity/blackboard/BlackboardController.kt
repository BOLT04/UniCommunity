package pt.isel.g20.unicommunity.blackboard

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.blackboard.model.MultipleBlackboardsResponse
import pt.isel.g20.unicommunity.blackboard.model.SingleBlackboardResponse
import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboard.model.BlackboardDto
import pt.isel.g20.unicommunity.blackboard.service.IBlackboardService
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.hateoas.Uri
import pt.isel.g20.unicommunity.hateoas.Uri.BLACKBOARDS_ROUTE
import pt.isel.g20.unicommunity.hateoas.Uri.SINGLE_BLACKBOARD_ROUTE
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class BlackboardController(private val service: IBlackboardService) {

    @GetMapping(path = [BLACKBOARDS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllBlackboards(@PathVariable boardId: Long) =
            service.getAllBlackboards(boardId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(MultipleBlackboardsResponse(boardId, it))
            }

    @GetMapping(path = [SINGLE_BLACKBOARD_ROUTE], produces = ["application/hal+json"])
    fun getBlackboardById(@PathVariable boardId: Long, @PathVariable bbId: Long) =
            service.getBlackboardById(boardId, bbId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBlackboardResponse(it))
            }

    @PostMapping(path = [BLACKBOARDS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBlackboard(@PathVariable boardId: Long, @RequestBody blackboardDto: BlackboardDto) =
            service.createBlackboard(
                    boardId,
                    blackboardDto.name,
                    blackboardDto.notificationLevel,
                    blackboardDto.description
            ).let {
                ResponseEntity
                        .created(Uri.forSingleBlackboard(it.board.id, it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBlackboardResponse(it))
            }

    @PutMapping(path = [SINGLE_BLACKBOARD_ROUTE], produces = ["application/hal+json"])
    fun editBlackboard(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @RequestBody blackboardDto: BlackboardDto
    ) =
            service.editBlackboard(
                    boardId,
                    bbId,
                    blackboardDto.name,
                    blackboardDto.notificationLevel,
                    blackboardDto.description
            ).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBlackboardResponse(it))
            }


    @DeleteMapping(path = [SINGLE_BLACKBOARD_ROUTE], produces = ["application/hal+json"])
    fun deleteBlackboard(@PathVariable boardId: Long, @PathVariable bbId: Long) =
            service.deleteBlackboard(boardId, bbId).let {
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(it.hashCode().toString())
                        .body(SingleBlackboardResponse(it))
            }


    @ExceptionHandler
    fun handleNotFoundBlackboardException(e: NotFoundBlackboardException) =
            ResponseEntity
                    .notFound()
                    .build<String>()

    @ExceptionHandler
    fun handleNotFoundBoardException(e: NotFoundBoardException) =
            ResponseEntity
                    .notFound()
                    .build<String>()
}