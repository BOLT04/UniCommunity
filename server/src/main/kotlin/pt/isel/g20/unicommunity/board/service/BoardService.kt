package pt.isel.g20.unicommunity.board.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.service.IBlackboardService
import pt.isel.g20.unicommunity.board.exception.InvalidTemplateConfigurationException
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.forum.service.IForumService
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.TemplateRepository
import pt.isel.g20.unicommunity.template.exception.NotFoundTemplateException
import pt.isel.g20.unicommunity.template.service.TemplateService

@Service
class BoardService(
        val boardsRepo: BoardRepository,
        val templatesRepo: TemplateRepository,
        val forumService: IForumService,
        val blackboardService: IBlackboardService,
        val templateService: TemplateService
) : IBoardService {

    override fun getAllBoards(): Iterable<Board> = boardsRepo.findAll()

    override fun getBoardById(boardId: Long) = boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
    
    override fun createBoard(
            name: String,
            templateId: Long?,
            description: String?,
            blackboardNames: List<String>?,
            hasForum: Boolean?): Board {

        val board: Board = if(templateId != null)
            createBoardWithTemplate(name,  description, templateId)
        else
            createBoardWithCustomModules(name, description, blackboardNames, hasForum)

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

    private fun createBoardWithCustomModules(
            name: String,
            description: String?,
            blackboardNames: List<String>?,
            hasForum: Boolean?
    ): Board {
        if (blackboardNames.isNullOrEmpty() || hasForum == null) throw InvalidTemplateConfigurationException()
        val templateName = name+"Template"
        val bbNames = blackboardNames.joinToString(",")
        val template = templateService.createTemplate(templateName, hasForum, bbNames)

        return createBoardWithTemplate(name, description, template.id)
    }

    private fun createBoardWithTemplate(
            name: String,
            description: String?,
            templateId: Long
    ): Board {
        val blackboards: MutableList<String> = mutableListOf()
        val template = templatesRepo.findByIdOrNull(templateId) ?: throw NotFoundTemplateException()
        blackboards.addAll(template.blackboardNames.split(","))

        val board = Board(name, templateId, description)

        val newBoard = boardsRepo.save(board)
        // Create all board modules
        if (template.hasForum) {
            val forum = forumService.createForum(newBoard.id, true)
            newBoard.forum = forum
        }

        newBoard.blackBoards.addAll(
                blackboards.map {
                    blackboardService.createBlackboard(newBoard.id, it, "TODO", "TODO")
                })

        return newBoard
    }

}