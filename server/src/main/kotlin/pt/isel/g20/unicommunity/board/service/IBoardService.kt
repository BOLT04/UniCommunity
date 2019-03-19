package pt.isel.g20.unicommunity.board.service

import pt.isel.g20.unicommunity.board.model.Board

interface IBoardService {
    fun getAllBoards() : Collection<Board>
    fun createBoard(board: Board)
    fun getBoardByName(boardName: String): Board?
}