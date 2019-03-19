package pt.isel.g20.unicommunity.board.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.board.model.Board

@Service
class BoardService : IBoardService {
    val boards = hashMapOf<String, Board>()

    private val logger = LoggerFactory.getLogger(BoardService::class.java)

    @Synchronized
    override fun getAllBoards() = boards.values

    @Synchronized
    override fun createBoard(board: Board) {
        boards[board.name] = board
    }

    override fun getBoardByName(boardName: String) = boards[boardName]
}