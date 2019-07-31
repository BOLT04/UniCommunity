package pt.isel.g20.unicommunity.board.service

import org.springframework.data.domain.Page
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.template.exception.NotFoundTemplateException
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException

interface IBoardService {
    fun getAllBoards() : Iterable<Board>
    fun getAllBoards(page: Int, pageSize: Int = 2) : Page<Board>

    @Throws(NotFoundBoardException::class)
    fun getBoardById(boardId: Long): Board

    @Throws(NotFoundTemplateException::class)
    fun createBoard(
            name: String,
            templateId: Long?,
            description: String?,
            blackboardNames: List<String>?,
            hasForum: Boolean?): Board

    @Throws(NotFoundBoardException::class)
    fun editBoard(boardId: Long, name: String?, description: String?): Board

    @Throws(NotFoundBoardException::class)
    fun deleteBoard(boardId: Long):Board

    @Throws(NotFoundBoardException::class, NotFoundUserException::class)
    fun addUserToBoard(boardId: Long, userId: Long, token: String): Board

    @Throws(NotFoundBoardException::class, NotFoundUserException::class)
    fun removeUserFromBoard(boardId: Long, userId: Long): Board
}