package pt.isel.g20.unicommunity.board.service

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboard.service.BlackboardService
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.fcm.GoogleServiceFactory
import pt.isel.g20.unicommunity.forum.service.ForumService
import pt.isel.g20.unicommunity.repository.*
import pt.isel.g20.unicommunity.user.model.User
import pt.isel.g20.unicommunity.usersBlackboards.model.UsersBlackboards
import pt.isel.g20.unicommunity.usersBoards.UsersBoards

@Service
class BoardService(
        val boardsRepo: BoardRepository,
        val templatesRepo: TemplateRepository,
        val blackboardService: BlackboardService,
        val usersBlackboardsRepo: UsersBlackboardsRepository,
        val usersBoardsRepo: UsersBoardsRepository,
        val usersRepo: UserRepository,
        val blackboardsRepo: BlackboardRepository,
        val forumService: ForumService
) {

    fun getActiveBoards(page: Int, pageSize: Int): Page<Board> =
            boardsRepo.findByActiveTrue(PageRequest.of(page, pageSize))

    fun getAllBoards(page: Int, pageSize: Int): Page<Board> =
            boardsRepo.findAll(PageRequest.of(page, pageSize))

    fun getMyBoards(userId: Long, page: Int, pageSize: Int): Page<Board> {
        usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()
        val userBoards = usersBoardsRepo.findByUserId(userId)
        return boardsRepo.findByUsersBoardsIn(userBoards, PageRequest.of(page, pageSize))
    }

    fun getBoardById(boardId: Long) =
            boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()

    fun createBoard(
            creatorId: Long,
            name: String,
            templateId: Long?,
            description: String?,
            blackboardNames: List<String>?,
            hasForum: Boolean?,
            fcmToken: String? = null
    ): Board {
        val board: Board = if(templateId != null)
            createBoardWithTemplate(creatorId, name,  description, templateId, fcmToken)
        else
            createBoardWithCustomModules(creatorId, name, blackboardNames, hasForum, description, fcmToken)

        return boardsRepo.save(board)
    }

    private fun createBoardWithTemplate(
            creatorId: Long,
            name: String,
            description: String?,
            templateId: Long,
            fcmToken: String? = null
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
            description: String?,
            fcmToken: String? = null
    ): Board {
        if (blackboardNames.isNullOrEmpty() || hasForum == null)
            throw InvalidTemplateConfigurationException()

        return finalStepCreateBoard(creatorId, name, blackboardNames, hasForum, description, fcmToken)
    }

    private fun finalStepCreateBoard(
            creatorId: Long,
            name: String,
            blackboardNames: List<String>,
            hasForum: Boolean,
            description: String?,
            fcmToken: String? = null
    ): Board {
        val creator = usersRepo.findByIdOrNull(creatorId) ?: throw NotFoundUserException()
        if(creator.role != ADMIN && creator.role != TEACHER) throw ForbiddenException()

        var board = Board(creator, name, description)
        boardsRepo.save(board)

        if (hasForum) {
            forumService.createForum(board.id)
            board = boardsRepo.save(board)
        }

        blackboardNames.map {
            blackboardService.createBlackboard(creatorId, board.id, it, "priority")
        }

        board = subscribe(board.id, creatorId, fcmToken)

        return boardsRepo.save(board)
    }

    val iidService = GoogleServiceFactory.makeIidService()

    fun subscribe(boardId: Long, userId: Long, token: String? = null): Board {
        var board = getBoardById(boardId)
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

        if(usersBoardsRepo.findByUserIdAndBoardId(userId, boardId) != null)
            throw AlreadyAMemberException()

        val userBoard = UsersBoards(user, board)
        usersBoardsRepo.save(userBoard)

        board.usersBoards.add(userBoard)
        user.usersBoards.add(userBoard)

        board = boardsRepo.save(board)
        usersRepo.save(user)

        board.blackBoards.forEach { item -> run {
            val userBlackboard = UsersBlackboards(user, item, item.notificationLevel)
            usersBlackboardsRepo.save(userBlackboard)

            user.blackboardsSettings.add(userBlackboard)
            usersRepo.save(user)

            item.usersSettings.add(userBlackboard)
            blackboardsRepo.save(item)
        } }
        return if (token == null)
            board
        else
            subscribeToFcm(boardId, userId, token)
    }

    fun subscribeToFcm(boardId: Long, userId: Long, token: String): Board{
        var board = getBoardById(boardId)

        if(usersBoardsRepo.findByUserIdAndBoardId(userId, boardId) == null)
            throw NotAMemberException()

        runBlocking {
            val promisses = board.blackBoards.map {
                async {
                    //TODO: This topic name has a problem. Since we are separating the ids by the char '-', then
                    //TODO: the blackboard name can't contain that character.
                    //TODO: Also, I'm only using the blackboard name so that the topic name is more readable (not just ids)
                    val topicName = "${board.id}-${it.id}-${it.name}"

                    iidService.subscribeAppToTopic(token, topicName)
                }
            }

            promisses.forEach {
                val rsp = it.await()
                println("in subscribe: ${rsp.code()}")
                if (!rsp.isSuccessful) throw SubscribeToTopicException()
            }

            println("in subscribe: returning")
            board
        }
        return board
    }

    fun unsubscribe(boardId: Long, userId: Long):Board {
        var board = getBoardById(boardId)
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()
        val userBoard = usersBoardsRepo.findByUserIdAndBoardId(userId, boardId) ?: throw NotAMemberException()

        usersBoardsRepo.delete(userBoard)

        board = boardsRepo.save(board)
        usersRepo.save(user)

        board.blackBoards.forEach { item -> run {
            val userBlackboard =
                    usersBlackboardsRepo.findByUserIdAndBlackboardId(userId, item.id) ?:
                        throw NotFoundUsersBlackboardsException()

            usersBlackboardsRepo.delete(userBlackboard)

            user.blackboardsSettings.remove(userBlackboard)
            usersRepo.save(user)

            item.usersSettings.remove(userBlackboard)
            blackboardsRepo.save(item)
        } }
        board = boardsRepo.save(board)

        runBlocking {
            val promisses = board.blackBoards.map {
                async {
                    //TODO: This topic name has a problem. Since we are separating the ids by the char '-', then
                    //TODO: the blackboard name can't contain that character.
                    //TODO: Also, I'm only using the blackboard name so that the topic name is more readable (not just ids)
                    val topicName = "${board.id}-${it.id}-${it.name}"

                    iidService.unsubscribeAppFromTopic(topicName)
                }
            }

            promisses.forEach {
                val rsp = it.await()
                println("in unsubscribe: ${rsp.code()}")
                if (!rsp.isSuccessful) throw SubscribeToTopicException()
            }

            println("in subscribe: returning")
            board
        }

        return board
    }

    fun editBoard(user: User, boardId: Long, name: String, isActive: Boolean, description: String?): Board {
        val board = getBoardById(boardId)
        val allowedToChangeState = (user.id == board.creator.id || user.role == ADMIN)
        if (!allowedToChangeState)
            throw ForbiddenException()

        board.name = name
        board.active = isActive
        if(description != null)
            board.description = description

        return boardsRepo.save(board)
    }

    fun deleteBoard(user: User, boardId: Long): Board {
        val board = getBoardById(boardId)
        val allowedToChangeState = (user.id == board.creator.id || user.role == ADMIN)
        if (!allowedToChangeState)
            throw ForbiddenException()

        boardsRepo.delete(board)
        return board
    }
}
