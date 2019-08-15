package pt.isel.g20.unicommunity.board

import com.sun.org.apache.xerces.internal.util.HTTPInputSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.board.exception.InvalidTemplateConfigurationException
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.exception.SubscribeToTopicException
import pt.isel.g20.unicommunity.board.model.*
import pt.isel.g20.unicommunity.board.service.IBoardService
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.Uri.BOARDS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.BOARD_MEMBERS
import pt.isel.g20.unicommunity.common.Uri.SINGLE_BOARD_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.template.exception.NotFoundTemplateException
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException
import pt.isel.g20.unicommunity.user.model.User
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class BoardController(private val service: IBoardService) {

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
            val promisses = blackboards.map {
                async {
                    service.getPosts()
                }
            }

            //TODO: Promise.all()??
            promisses.forEach {
                it.await()
            }
            emptyList<Post>()
        }
    }




    @AuthorizationRequired
    @GetMapping(path = [BOARDS_ROUTE], produces = ["application/vnd.collection+json"])
    fun getAllBoards(
            @SessionAttribute("user") user: User,
            @RequestParam(value = "page", required = false, defaultValue = "0") page: Int
    ) : ResponseEntity<CollectionObject> =
        service
                .getAllBoards(page)
                .map(Board::toItemRepr).let {
            val response = CollectionObject(MultipleBoardsResponse(it, page))

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
    fun getBoardById(@PathVariable boardId: Long,  @SessionAttribute("user") user: User) =
            service.getBoardById(boardId).let {
                val response = SingleBoardResponse(user, it)

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
    fun createBoard(@RequestBody boardDto: BoardDto, @SessionAttribute("user")user: User) =
            service.createBoard(
                    user.id,
                    boardDto.name,
                    boardDto.templateId,
                    boardDto.description,
                    boardDto.blackboardNames,
                    boardDto.hasForum).let {
                val response = SingleBoardResponse(user, it)

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
    fun editBoard(@PathVariable boardId: Long,
                  @RequestBody boardDto: BoardDto,
                  @SessionAttribute("user") user: User
    ) =
            service.editBoard(boardId, boardDto.name, boardDto.description).let {
                val response = SingleBoardResponse(user, it)

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
    fun deleteBoard(@PathVariable boardId: Long, @SessionAttribute("user") user: User) =
            service.deleteBoard(boardId).let {
                val response = SingleBoardResponse(user, it)

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
            @SessionAttribute("user") user: User,
            @RequestBody subscribeDto: SubscribeDto): ResponseEntity<SingleBoardResponse>{
        return service.addUserToBoard(boardId, user.id, subscribeDto.token).let {
            val response = SingleBoardResponse(user, it)

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
    @PostMapping(path = [SINGLE_BOARD_ROUTE], produces = ["application/hal+json"])//TODO: should be a delete? Maybe not since no resource is created in addUserToBoard, the processing is add to a list and remove
    fun removeUserFromBoard(
            @PathVariable boardId: Long,
            @SessionAttribute("user") user: User): ResponseEntity<SingleBoardResponse>{
        return service.removeUserFromBoard(boardId, user.id).let {
            val response = SingleBoardResponse(user, it)

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
                    .status(HttpStatus.NOT_FOUND)
                    .body("Board was not found")

    @ExceptionHandler
    fun handleNotFoundTemplateException(e: NotFoundTemplateException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Template was not found")

    @ExceptionHandler
    fun handleNotFoundUserException(e: NotFoundUserException) =
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User was not found")

    @ExceptionHandler
    fun handleInvalidTemplateConfigurationException(e: InvalidTemplateConfigurationException) =
            ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The provided configuration is invalid")

    @ExceptionHandler
    fun handleSubscribeToTopicException(e: SubscribeToTopicException) =
            ResponseEntity
                    .status(HttpStatus.BAD_GATEWAY)
                    .body("An error occured when trying to join the board")

}