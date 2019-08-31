package pt.isel.g20.unicommunity.board.service

import org.springframework.data.domain.Page
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.common.*

interface IBoardService {
    fun getAllBoards() : Iterable<Board>
    fun getAllBoards(page: Int, pageSize: Int = 1000) : Page<Board> // TODO: 1000 boards per page temporarily

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
    fun editBoard(boardId: Long, name: String?, description: String?): Board

    @Throws(NotFoundBoardException::class)
    fun deleteBoard(boardId: Long):Board
}