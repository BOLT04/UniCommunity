package pt.isel.g20.unicommunity.board.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.TemplateRepository
import pt.isel.g20.unicommunity.template.exception.NotFoundTemplateException

@Service
class BoardService(val boardsRepo: BoardRepository, val templatesRepo: TemplateRepository) : IBoardService {
    override fun getAllBoards(): Iterable<Board> = boardsRepo.findAll()

    override fun getBoardById(boardId: Long) = boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()

    override fun createBoard(name: String, templateId: Long, description: String?): Board {
        templatesRepo.findByIdOrNull(templateId) ?: throw NotFoundTemplateException()

        val board =
                if(description != null)
                    Board(name, templateId, description)
                else
                    Board(name, templateId)

        return boardsRepo.save(board)
    }

    override fun editBoard(boardId: Long, name: String?, description: String?): Board {
        val board = getBoardById(boardId)

        if(name != null)
            board.name = name

        if(description != null)
            board.description = description

        return boardsRepo.save(board)
    }

    override fun deleteBoard(boardId: Long): Board {
        val board = getBoardById(boardId)
        boardsRepo.delete(board)
        return board
    }

}