package pt.isel.g20.unicommunity.board.service

import org.springframework.data.domain.Page
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.user.model.User

interface IBoardService {
    fun getActiveBoards(page: Int, pageSize: Int = 1000) : Page<Board>

    fun getAllBoards(page: Int, pageSize: Int = 1000) : Page<Board> // TODO: 1000 boards per page temporarily

    fun getMyBoards(userId: Long, page: Int, pageSize: Int = 1000) : Page<Board>

    @Throws(NotFoundBoardException::class)
    fun getBoardById(boardId: Long): Board

    @Throws(
            NotFoundTemplateException::class,
            InvalidTemplateConfigurationException::class,
            NotFoundUserException::class
    )
    fun createBoard(
            creatorId: Long,
            name: String,
            templateId: Long?,
            description: String?,
            blackboardNames: List<String>?,
            hasForum: Boolean?
    ): Board

    @Throws(
            NotFoundBoardException::class,
            NotFoundUserException::class,
            SubscribeToTopicException::class
    )
    fun subscribe(boardId: Long, userId: Long, token: String): Board

    @Throws(NotFoundBoardException::class, NotFoundUserException::class)
    fun unsubscribe(boardId: Long, userId: Long): Board

    @Throws(NotFoundBoardException::class)
    fun editBoard(user: User, boardId: Long, name: String, isActive: Boolean, description: String?): Board

    @Throws(NotFoundBoardException::class)
    fun deleteBoard(user: User, boardId: Long):Board
}