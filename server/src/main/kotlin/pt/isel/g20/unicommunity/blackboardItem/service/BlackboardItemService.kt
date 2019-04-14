package pt.isel.g20.unicommunity.BlackboardItem.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.blackboardItem.exception.NotFoundBlackboardItemException
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import pt.isel.g20.unicommunity.blackboardItem.service.IBlackboardItemService
import pt.isel.g20.unicommunity.repository.BlackboardItemRepository

@Service
class BlackboardItemService(val blackboardItemRepo: BlackboardItemRepository) : IBlackboardItemService {
    override fun getAllBlackboardItems(boardId: Long, bbId: Long): Iterable<BlackboardItem> = blackboardItemRepo.findAll()

    override fun getBlackboardItemById(boardId: Long, bbId: Long, itemId: Long) = blackboardItemRepo.findByIdOrNull(itemId) ?: throw NotFoundBlackboardItemException()

    override fun createBlackboardItem(boardId: Long, bbId: Long, name: String, content: String): BlackboardItem {
        val blackboardItem = BlackboardItem(boardId, bbId, name, content)
        return blackboardItemRepo.save(blackboardItem)
    }

    override fun editBlackboardItem(boardId: Long, bbId: Long, itemId: Long, name: String?, content: String?): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)

        if(name != null)
            blackboardItem.name = name

        if(content != null)
            blackboardItem.content = content

        return blackboardItemRepo.save(blackboardItem)
    }

    override fun deleteBlackboardItem(boardId: Long, bbId: Long, itemId: Long): BlackboardItem {
        val blackboardItem = getBlackboardItemById(boardId, bbId, itemId)
        blackboardItemRepo.delete(blackboardItem)
        return blackboardItem
    }

}