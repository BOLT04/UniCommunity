package pt.isel.g20.unicommunity.blackboard

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.blackboard.exception.NotFoundBlackboardException
import pt.isel.g20.unicommunity.blackboard.model.*
import pt.isel.g20.unicommunity.blackboard.service.IBlackboardService
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.Uri.BLACKBOARDS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_BLACKBOARD_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.user.model.User
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class BlackboardController(private val service: IBlackboardService) {

    @AuthorizationRequired
    @GetMapping(path = [BLACKBOARDS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllBlackboards(@PathVariable boardId: Long) =
            service.getAllBlackboards(boardId).map(Blackboard::toItemRepr).let {
                val response = CollectionObject(MultipleBlackboardsResponse(boardId, it))

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
    @GetMapping(path = [SINGLE_BLACKBOARD_ROUTE], produces = ["application/hal+json"])
    fun getBlackboardById(@PathVariable boardId: Long,
                          @PathVariable bbId: Long,
                          @SessionAttribute("user") user: User
    ) =
            service.getBlackboardById(boardId, bbId).let {
                val response = SingleBlackboardResponse(user, it)

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
    @PostMapping(path = [BLACKBOARDS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBlackboard(@PathVariable boardId: Long,
                         @RequestBody blackboardDto: BlackboardDto,
                         @SessionAttribute("user") user: User
    ) =
            service.createBlackboard(
                    boardId,
                    blackboardDto.name,
                    blackboardDto.notificationLevel,
                    blackboardDto.description
            ).let {
                val response = SingleBlackboardResponse(user, it)

                ResponseEntity
                        .created(Uri.forSingleBlackboardUri(it.board.id, it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_BLACKBOARD_ROUTE], produces = ["application/hal+json"])
    fun editBlackboard(
            @PathVariable boardId: Long,
            @PathVariable bbId: Long,
            @RequestBody blackboardDto: BlackboardDto,
            @SessionAttribute("user") user: User
    ) =
            service.editBlackboard(
                    boardId,
                    bbId,
                    blackboardDto.name,
                    blackboardDto.notificationLevel,
                    blackboardDto.description
            ).let {
                val response = SingleBlackboardResponse(user, it)

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
    @DeleteMapping(path = [SINGLE_BLACKBOARD_ROUTE], produces = ["application/hal+json"])
    fun deleteBlackboard(@PathVariable boardId: Long,
                         @PathVariable bbId: Long,
                         @SessionAttribute("user") user: User
    ) =
            service.deleteBlackboard(boardId, bbId).let {
                val response = SingleBlackboardResponse(user, it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
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