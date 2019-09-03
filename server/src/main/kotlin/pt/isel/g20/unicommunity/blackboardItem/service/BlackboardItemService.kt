package pt.isel.g20.unicommunity.blackboardItem.service

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.fcm.FcmMessage
import pt.isel.g20.unicommunity.fcm.GoogleServiceFactory
import pt.isel.g20.unicommunity.repository.*
import pt.isel.g20.unicommunity.user.model.User

@Service
class BlackboardItemService(
        private val boardsRepo: BoardRepository,
        private val blackboardsRepo: BlackboardRepository,
        private val blackboardItemsRepo: BlackboardItemRepository,
        private val usersRepo: UserRepository,
        private val userBlackboardRepo: UsersBlackboardsRepository
) : IBlackboardItemService {
    override fun getAllBlackboardItems(boardId: Long, bbId: Long): Iterable<BlackboardItem> {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        return blackboardItemsRepo.findByBlackboardIdOrderByCreatedAtDesc(bbId).asIterable()
    }

    override fun getBlackboardItemById(boardId: Long, bbId: Long, itemId: Long) : BlackboardItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        return blackboardItemsRepo.findByBlackboardIdAndId(bbId, itemId) ?: throw NotFoundBlackboardItemException()
    }

    private val fcmService = GoogleServiceFactory.makeFcmService()
    override fun createBlackboardItem(
            boardId: Long,
            bbId: Long,
            userId: Long,
            name: String,
            content: String
    ): BlackboardItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        val blackboard = blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()

        val blackboardItem = BlackboardItem(name, content, user, blackboard)
        val newBlackboardItem =  blackboardItemsRepo.save(blackboardItem)

        user.bbItems.add(newBlackboardItem)
        usersRepo.save(user)
        blackboard.items.add(newBlackboardItem)
        blackboardsRepo.save(blackboard)

        val userConfigs = userBlackboardRepo.findByUserIdAndBlackboardId(userId, blackboard.id)

        return runBlocking {
            val promise = async {
                val body = FcmMessage(
                        to = "/topics/${blackboard.name}",
                        data = mutableMapOf(
                                "notificationLevel" to userConfigs.notificationLevel
                        ),
                        notification = mutableMapOf(
                                "title" to name,
                                "body" to content
                        )
                )
                fcmService.sendMessageToTopic(body)
            }

            val rsp = promise.await()
            if (!rsp.isSuccessful) throw SubscribeToTopicException()

            newBlackboardItem
        }
    }

    override fun editBlackboardItem(
            user: User,
            boardId: Long,
            bbId: Long,
            itemId: Long,
            name: String?,
            content: String?
    ): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)
        if(user.id != blackboardItem.blackboard.board.creator.id && user.role != ADMIN) throw UnauthorizedException()

        if(name != null)
            blackboardItem.name = name

        if(content != null)
            blackboardItem.content = content

        return blackboardItemsRepo.save(blackboardItem)
    }

    override fun deleteBlackboardItem(user: User, boardId: Long, bbId: Long, itemId: Long): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)
        if(user.id != blackboardItem.blackboard.board.creator.id && user.role != ADMIN) throw UnauthorizedException()

        blackboardItemsRepo.delete(blackboardItem)
        return blackboardItem
    }

}