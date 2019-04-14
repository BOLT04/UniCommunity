package pt.isel.g20.unicommunity.board.service

import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.model.Board

interface IBoardService {
    fun getAllBoards() : Iterable<Board>

    @Throws(NotFoundBoardException::class)
    fun getBoardById(boardId: Long): Board

    fun createBoard(name: String, description: String?): Board

    @Throws(NotFoundBoardException::class)
    fun editBoard(boardId: Long, name: String?, description: String?): Board

    @Throws(NotFoundBoardException::class)
    fun deleteBoard(boardId: Long):Board
}