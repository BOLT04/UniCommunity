package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem
import javax.transaction.Transactional

@Repository
@Transactional
interface BlackboardItemRepository : CrudRepository<BlackboardItem, Long>{
    fun findByName(name: String?): List<BlackboardItem>?
}