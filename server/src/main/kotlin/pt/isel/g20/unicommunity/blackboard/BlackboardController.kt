package pt.isel.g20.unicommunity.blackboard

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import pt.isel.g20.unicommunity.blackboard.model.BlackboardDto
import pt.isel.g20.unicommunity.blackboard.model.BlackboardLinksResponse
import pt.isel.g20.unicommunity.blackboard.model.BlackboardResponse
import pt.isel.g20.unicommunity.blackboard.service.IBlackboardService

private const val LIST_BLACKBOARDS_ROUTE = "/api/blackboards"
private const val GET_BOARD_ROUTE = "/api/blackboards/{bbId}"

@RestController
class BlackboardController(private val service: IBlackboardService) {

    @GetMapping(path = [LIST_BLACKBOARDS_ROUTE])
    fun getAllBlackboards() = service.getAllBlackboards()

    @PostMapping(path = [LIST_BLACKBOARDS_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBlackboard(@RequestBody blackboardDto: BlackboardDto): BlackboardLinksResponse {
        service.createBlackboard(blackboardDto.toModel())

        return BlackboardLinksResponse(
                "$LIST_BLACKBOARDS_ROUTE/${blackboardDto.id}"
        )
    }

    @RequestMapping(GET_BOARD_ROUTE, method = [RequestMethod.GET])
    fun getBlackboardById(@PathVariable(value="bbId") boardId: String) : BlackboardResponse {
        val blackboard = service.getBlackboardById(boardId)
                ?: throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A blackboard with the given ID couldn't be found.")

        return BlackboardResponse(blackboard.id, blackboard.name, blackboard.description, blackboard.notificationLevel,
                "$LIST_BLACKBOARDS_ROUTE/${blackboard.id}"
        )
    }
}