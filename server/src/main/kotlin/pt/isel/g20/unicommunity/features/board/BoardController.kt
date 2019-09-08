package pt.isel.g20.unicommunity.features.board

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.ACTIVE_BOARDS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.BOARDS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.BOARD_MEMBERS
import pt.isel.g20.unicommunity.common.Uri.FCM_SUBSCRIBE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_BOARD_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.features.board.model.*
import pt.isel.g20.unicommunity.features.board.service.BoardService
import pt.isel.g20.unicommunity.features.retrofit.Post
import pt.isel.g20.unicommunity.features.retrofit.RetrofitFactory
import pt.isel.g20.unicommunity.features.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class BoardController(private val service: BoardService) {


    @AuthorizationRequired
    @GetMapping(path = [ACTIVE_BOARDS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getActiveBoards(
            @SessionAttribute("user") user: User,
            @RequestParam(value = "page", required = false, defaultValue = "0") page: Int
    ) =
            okResponse(
                    CollectionObject(
                            MultipleBoardsResponse(
                                    service
                                            .getActiveBoards(page, 1000)
                                            .map { it.toItemRepr(user) },
                                    page
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [BOARDS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllBoards(
            @SessionAttribute("user") user: User,
            @RequestParam(value = "page", required = false, defaultValue = "0") page: Int
    ) =
            okResponse(
                    CollectionObject(
                            MultipleBoardsResponse(
                                    service
                                            .getAllBoards(page, 1000)
                                            .map { it.toItemRepr(user) },
                                    page
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [Uri.MY_BOARDS], produces = [APPLICATION_COLLECTION_JSON])
    fun getMyBoards(
            @SessionAttribute("user") user: User,
            @RequestParam(value = "page", required = false, defaultValue = "0") page: Int
    ) =
            okResponse(
                    CollectionObject(
                            MultipleBoardsResponse(
                                    service.getMyBoards(user.id, page, 1000).map { it.toItemRepr(user) },
                                    page
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [SINGLE_BOARD_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getBoardById(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User
    ) =
            okResponse(SingleBoardResponse(user, service.getBoardById(boardId)))

    @AuthorizationRequired
    @PostMapping(path = [BOARDS_ROUTE], produces = [APPLICATION_HAL_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createBoard(
            @RequestBody boardDto: CreateBoardDto,
            @SessionAttribute("user")user: User
    ) =
            service.createBoard(
                    user.id,
                    boardDto.name,
                    boardDto.templateId,
                    boardDto.description,
                    boardDto.blackboardNames,
                    boardDto.hasForum,
                    boardDto.fcmToken
            ).let {
                val responseBody = SingleBoardResponse(user, it)
                val newResourceHref = Uri.forSingleBoardUri(it.id)
                createdResponse(responseBody, newResourceHref)
            }

    @AuthorizationRequired
    @PostMapping(path = [BOARD_MEMBERS], produces = [APPLICATION_HAL_JSON])
    fun subscribe(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User,
            @RequestBody subscribeDto: SubscribeDto?
    ) =
            okResponse(
                    SubscribeResponse(
                            service.subscribe(boardId, user.id, subscribeDto?.fcmToken)
                    )
            )

    @AuthorizationRequired
    @PostMapping(path = [FCM_SUBSCRIBE], produces = [APPLICATION_HAL_JSON])
    fun subscribeToFcm(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User,
            @RequestBody subscribeDto: SubscribeDto
    ) =
            okResponse(
                    SubscribeResponse(
                            service.subscribeToFcm(boardId, user.id, subscribeDto.fcmToken)
                    )
            )

    @AuthorizationRequired
    @DeleteMapping(path = [BOARD_MEMBERS], produces = [APPLICATION_HAL_JSON])
    fun unsubscribe(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User,
            @RequestBody subscribeDto: SubscribeDto?
    ) =
            okResponse(
                    UnsubscribeResponse(
                            service.unsubscribe(boardId, user.id, subscribeDto?.fcmToken)
                    )
            )

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_BOARD_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editBoard(
            @PathVariable boardId: Long,
            @RequestBody boardDto: BoardDto,
            @SessionAttribute("user") user: User
    ) =
            okResponse(
                    SingleBoardResponse(
                            user,
                            service.editBoard(
                                    user,
                                    boardId,
                                    boardDto.name,
                                    boardDto.isActive,
                                    boardDto.description
                            )
                    )
            )

    @AuthorizationRequired
    @DeleteMapping(path = [SINGLE_BOARD_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun deleteBoard(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User
    ) =
            okResponse(SingleBoardResponse(user, service.deleteBoard(user, boardId)))
}