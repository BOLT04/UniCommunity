package pt.isel.g20.unicommunity.features.forum

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.FORUM_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.features.forum.model.SingleForumResponse
import pt.isel.g20.unicommunity.features.forum.service.ForumService
import pt.isel.g20.unicommunity.features.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class ForumController(private val service: ForumService) {

    @AuthorizationRequired
    @GetMapping(path = [FORUM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getForumById(@PathVariable boardId: Long, @SessionAttribute("user")user: User) =
            okResponse(SingleForumResponse(service.getForumById(boardId)))

    @AuthorizationRequired
    @PostMapping(path = [FORUM_ROUTE], produces = [APPLICATION_HAL_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createForum(@PathVariable boardId: Long, @SessionAttribute("user")user: User) =
            service.createForum(boardId).let {
                val responseBody = SingleForumResponse(it)
                val newResourceHref = Uri.forSingleForumUri(it.board.id)
                createdResponse(responseBody, newResourceHref)
            }

    @AuthorizationRequired
    @PutMapping(path = [FORUM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editForum(@PathVariable boardId: Long, @SessionAttribute("user")user: User) =
            okResponse(SingleForumResponse(service.editForum(user, boardId)))

    @AuthorizationRequired
    @DeleteMapping(path = [FORUM_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun deleteForum(@PathVariable boardId: Long, @SessionAttribute("user")user: User) =
            okResponse(SingleForumResponse(service.deleteForum(user, boardId)))
}