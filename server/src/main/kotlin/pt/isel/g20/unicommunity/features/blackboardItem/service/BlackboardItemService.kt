package pt.isel.g20.unicommunity.features.blackboardItem.service

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.features.fcm.FcmMessage
import pt.isel.g20.unicommunity.features.fcm.GoogleServiceFactory
import pt.isel.g20.unicommunity.features.user.model.User
import pt.isel.g20.unicommunity.repository.BlackboardItemRepository
import pt.isel.g20.unicommunity.repository.BlackboardRepository
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository

@Service
class BlackboardItemService(
        val boardsRepo: BoardRepository,
        val blackboardsRepo: BlackboardRepository,
        val blackboardItemsRepo: BlackboardItemRepository,
        val usersRepo: UserRepository
) {
    fun getAllBlackboardItems(boardId: Long, bbId: Long): Iterable<BlackboardItem> {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        return blackboardItemsRepo.findByBlackboardIdOrderByCreatedAtDesc(bbId).asIterable()
    }

    fun getBlackboardItemById(boardId: Long, bbId: Long, itemId: Long) : BlackboardItem {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        blackboardsRepo.findByIdOrNull(bbId) ?: throw NotFoundBlackboardException()
        return blackboardItemsRepo.findByBlackboardIdAndId(bbId, itemId) ?: throw NotFoundBlackboardItemException()
    }

    val fcmService = GoogleServiceFactory.makeFcmService()

    fun createBlackboardItem(
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

        runBlocking {
            val promise =
                    async {
                        fcmService.sendMessageToTopic(
                                FcmMessage(
                                        to = "/topics/${blackboard.getFcmTopicName()}",
                                        data = mutableMapOf(
                                                "notificationLevel" to blackboard.notificationLevel
                                        ),
                                        notification = mutableMapOf(
                                                "title" to blackboardItem.name,
                                                "body" to blackboardItem.content
                                        )
                                )
                        )
                    }

            val rsp = promise.await()
            println("sending fcm message: ${rsp.code()}")
            if (!rsp.isSuccessful) throw SubscribeToTopicException()

            println("in create BlackboardItem: returning")
            blackboard
        }

        return newBlackboardItem
    }

    fun editBlackboardItem(
            user: User,
            boardId: Long,
            bbId: Long,
            itemId: Long,
            name: String?,
            content: String?
    ): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)
        if(user.id != blackboardItem.blackboard.board.creator.id && user.role != ADMIN) throw ForbiddenException()

        if(name != null)
            blackboardItem.name = name

        if(content != null)
            blackboardItem.content = content

        return blackboardItemsRepo.save(blackboardItem)
    }

    fun deleteBlackboardItem(user: User, boardId: Long, bbId: Long, itemId: Long): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)
        if(user.id != blackboardItem.blackboard.board.creator.id && user.role != ADMIN) throw ForbiddenException()

        blackboardItemsRepo.delete(blackboardItem)
        return blackboardItem
    }

}