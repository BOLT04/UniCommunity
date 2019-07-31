package pt.isel.g20.unicommunity.board.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.service.IBlackboardService
import pt.isel.g20.unicommunity.board.RetrofitFactory
import pt.isel.g20.unicommunity.board.exception.InvalidTemplateConfigurationException
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.board.exception.SubscribeToTopicException
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.fcm.FcmServiceFactory
import pt.isel.g20.unicommunity.forum.service.IForumService
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.TemplateRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.template.exception.NotFoundTemplateException
import pt.isel.g20.unicommunity.template.service.TemplateService
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException

@Service
class BoardService(
        val boardsRepo: BoardRepository,
        val templatesRepo: TemplateRepository,
        val forumService: IForumService,
        val blackboardService: IBlackboardService,
        val templateService: TemplateService,
        val usersRepo: UserRepository
) : IBoardService {

    override fun getAllBoards(): Iterable<Board> = boardsRepo.findAll()

    override fun getAllBoards(page: Int, pageSize: Int): Page<Board> =
        boardsRepo.findAll(PageRequest.of(page, pageSize))

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

        val board = Board(name, template, description)
        var newBoard = boardsRepo.save(board)

        template.boards.add(newBoard)
        templatesRepo.save(template)

        if (template.hasForum) {
            forumService.createForum(newBoard.id, true)
            newBoard = boardsRepo.save(board)
        }

        blackboards.map {
            blackboardService.createBlackboard(newBoard.id, it, "TODO", "TODO")
        }

        return boardsRepo.save(newBoard)
    }

    val fcmService = FcmServiceFactory.makeFcmServiceService()

    override fun addUserToBoard(boardId: Long, userId: Long, token: String): Board {
        val board = getBoardById(boardId)
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

        board.members.add(user)
        user.boards.add(board)

        val newBoard = boardsRepo.save(board)
        usersRepo.save(user)

        return runBlocking {
            val promisses = board.blackBoards.map {
                async {
                    //TODO: This topic name has a problem. Since we are separating the ids by the char '-', then
                    //TODO: the blackboard name can't contain that character.
                    //TODO: Also, I'm only using the blackboard name so that the topic name is more readable (not just ids)
                    val topicName = "${board.id}-${it.id}-${it.name}"

                    fcmService.subscribeAppToTopic(token, topicName)
                }
            }

            promisses.forEach {
                val rsp = it.await()
                println("in addUserToBoard: ${rsp.code()}")
                if (!rsp.isSuccessful) throw SubscribeToTopicException()
            }

            println("in addUserToBoard: returning")
            newBoard
        }
    }

    override fun removeUserFromBoard(boardId: Long, userId: Long):Board {
        val board = getBoardById(boardId)
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

        board.members.remove(user)
        user.boards.remove(board)

        val newBoard = boardsRepo.save(board)
        usersRepo.save(user)

        return newBoard
    }
}