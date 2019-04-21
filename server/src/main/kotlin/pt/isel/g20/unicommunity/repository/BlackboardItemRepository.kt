package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import pt.isel.g20.unicommunity.blackboardItem.model.BlackboardItem

interface BlackboardItemRepository : CrudRepository<BlackboardItem, Long>