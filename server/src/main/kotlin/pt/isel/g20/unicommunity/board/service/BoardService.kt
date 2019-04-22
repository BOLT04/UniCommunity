package pt.isel.g20.unicommunity.board.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.model.Blackboard
import pt.isel.g20.unicommunity.blackboard.service.IBlackboardService
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.forum.service.IForumService
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.TemplateRepository
import pt.isel.g20.unicommunity.template.exception.NotFoundTemplateException

@Service
class BoardService(
        val boardsRepo: BoardRepository,
        val templatesRepo: TemplateRepository,
        val forumService: IForumService,
        val blackboardService: IBlackboardService) : IBoardService {

    override fun getAllBoards(): Iterable<Board> = boardsRepo.findAll()

    override fun getBoardById(boardId: Long) = boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()

    override fun createBoard(
            name: String,
            templateId: Long?,
            description: String?,
            blackboardNames: List<String>?,
            hasForum: Boolean?): Board {
        // Validate input
        // Check if the user sent both a template and module names
        if (templateId != null && blackboardNames != null && blackboardNames.isNotEmpty())
            throw RuntimeException("Please only choose one option...")

        // Check if the user sent neither a template and module names
        if (templateId == null && blackboardNames == null)
            throw RuntimeException("Please choose one option...")

        //TODO: add validation: You cant create a board with no modules! if (hasForum == null || !hasForum) && blackboardNames.isEmpty()

        val blackboards: MutableList<String> = mutableListOf()
        var hasForumAux = hasForum != null

        if (templateId != null) {
            val template = templatesRepo.findByIdOrNull(templateId) ?: throw NotFoundTemplateException()
            if (template.hasForum)
                hasForumAux = true

            blackboards.addAll(template.blackboardNames.split(","))
        } else if (blackboardNames != null && blackboardNames.isNotEmpty())
            blackboards.addAll(blackboardNames)

        val board = Board(name, templateId, description)

        val newBoard = boardsRepo.save(board)
        // Create all board modules
        if (hasForumAux) {
            val forum = forumService.createForum(newBoard.id, true)
            newBoard.forum = forum
        }

        newBoard.blackBoards.addAll(
                blackboards.map {
                    blackboardService.createBlackboard(newBoard.id, it, "TODO", "TODO")
                })

        return boardsRepo.save(newBoard)
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