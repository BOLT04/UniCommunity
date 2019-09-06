package pt.isel.g20.unicommunity.board

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.model.*
import pt.isel.g20.unicommunity.board.service.BoardService
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.ACTIVE_BOARDS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.BOARDS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.BOARD_MEMBERS
import pt.isel.g20.unicommunity.common.Uri.FCM_SUBSCRIBE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_BOARD_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class BoardController(private val service: BoardService) {

    @GetMapping(path = ["/retrofit"])
    fun getRetrofit(): List<Post>? {
        val service = RetrofitFactory.makeRetrofitService()
        return runBlocking {
            val response = withContext(Dispatchers.IO) {
                service.getPosts()
            }

            //if (response.isSuccessful)
                response.body()
            //} else {
            //    emptyList<List<Post>>()//"Error: ${response.code()}"
            //}
/*
            try {
                if (response.isSuccessful) {
                    response.body()
                } else {
                    emptyList<List<Post>>()//"Error: ${response.code()}"
                }
            } catch (e: HttpException) {
                emptyList<List<Post>>()//return "Exception ${e.message}"
            } catch (e: Throwable) {
                emptyList<List<Post>>()//return "Ooops: Something else went wrong"
            }*/
        }
    }

    @GetMapping(path = ["/retrofit/parallel"])
    fun getRetrofitParallel(): List<Post> {
        val service = RetrofitFactory.makeRetrofitService()
        return runBlocking {
            val blackboards = arrayOf(1, 2, 3, 4)
            val promises = blackboards.map {
                async {
                    service.getPosts()
                }
            }

            //TODO: Promise.all()??
            promises.forEach {
                it.await()
            }
            emptyList<Post>()
        }
    }

    @AuthorizationRequired
    @GetMapping(path = [ACTIVE_BOARDS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getActiveBoards(
            @SessionAttribute("user") user: User,
            @RequestParam(value = "page", required = false, defaultValue = "0") page: Int
    ) =
            cacheOkResponse(
                    CollectionObject(
                            MultipleBoardsResponse(
                                    service
                                            .getActiveBoards(page, 1000)
                                            .map{it.toItemRepr(user)},
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
            cacheOkResponse(
                    CollectionObject(
                            MultipleBoardsResponse(
                                    service
                                            .getAllBoards(page, 1000)
                                            .map{it.toItemRepr(user)},
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
            cacheOkResponse(
                    CollectionObject(
                            MultipleBoardsResponse(
                                    service.getMyBoards(user.id, page, 1000).map{it.toItemRepr(user)},
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
            cacheOkResponse(SingleBoardResponse(user, service.getBoardById(boardId)))

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
                cacheCreatedResponse(responseBody, newResourceHref)
            }

    @AuthorizationRequired
    @PostMapping(path = [BOARD_MEMBERS], produces = [APPLICATION_HAL_JSON])
    fun subscribe(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User,
            @RequestBody subscribeDto: SubscribeDto?
    ) =
            cacheOkResponse(
                    SubscribeResponse(
                            service.subscribe(boardId, user.id, subscribeDto?.token)
                    )
            )

    @AuthorizationRequired
    @PostMapping(path = [FCM_SUBSCRIBE], produces = [APPLICATION_HAL_JSON])
    fun subscribeToFcm(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User,
            @RequestBody subscribeDto: SubscribeDto
    ) =
            cacheOkResponse(
                    SubscribeResponse(
                            service.subscribeToFcm(boardId, user.id, subscribeDto.token)
                    )
            )

    @AuthorizationRequired
    @DeleteMapping(path = [BOARD_MEMBERS], produces = [APPLICATION_HAL_JSON])
    fun unsubscribe(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
                    UnsubscribeResponse(
                            service.unsubscribe(boardId, user.id)
                    )
            )

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_BOARD_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editBoard(
            @PathVariable boardId: Long,
            @RequestBody boardDto: BoardDto,
            @SessionAttribute("user") user: User
    ) =
            cacheOkResponse(
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
            cacheOkResponse(SingleBoardResponse(user, service.deleteBoard(user, boardId)))
}