package pt.isel.g20.unicommunity.board.service

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.service.IBlackboardService
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.fcm.FcmServiceFactory
import pt.isel.g20.unicommunity.forum.service.IForumService
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.TemplateRepository
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.template.service.TemplateService

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
            creatorId: Long,
            name: String,
            templateId: Long?,
            description: String?,
            blackboardNames: List<String>?,
            hasForum: Boolean?
    ): Board {

        val board: Board = if(templateId != null)
            createBoardWithTemplate(creatorId, name,  description, templateId)
        else
            createBoardWithCustomModules(creatorId, name, blackboardNames, hasForum, description)

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

    private fun createBoardWithTemplate(
            creatorId: Long,
            name: String,
            description: String?,
            templateId: Long
    ): Board {
        val template = templatesRepo.findByIdOrNull(templateId) ?: throw NotFoundTemplateException()

        val blackboardNames: MutableList<String> = mutableListOf()
        blackboardNames.addAll(template.blackboardNames.split(","))

        return createBoardWithCustomModules(creatorId, name, blackboardNames, template.hasForum, description)
    }

    private fun createBoardWithCustomModules(
            creatorId: Long,
            name: String,
            blackboardNames: List<String>?,
            hasForum: Boolean?,
            description: String?
    ): Board {
        if (blackboardNames.isNullOrEmpty() || hasForum == null)
            throw InvalidTemplateConfigurationException()

        return createBoard(creatorId, name, blackboardNames, hasForum, description)
    }

    private fun createBoard(
            creatorId: Long,
            name: String,
            blackboardNames: List<String>,
            hasForum: Boolean,
            description: String?
    ): Board {
        val creator = usersRepo.findByIdOrNull(creatorId) ?: throw NotFoundUserException()
        var board = Board(creator, name, description)
        board = boardsRepo.save(board)

        creator.boards.add(board)
        usersRepo.save(creator)

        if (hasForum) {
            forumService.createForum(board.id)
            board = boardsRepo.save(board)
        }

        blackboardNames.map {
            blackboardService.createBlackboard(board.id, it, "TODO", "TODO")
        }

        return boardsRepo.save(board)
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