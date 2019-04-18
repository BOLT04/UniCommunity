package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import pt.isel.g20.unicommunity.forumItem.model.ForumItem

interface ForumItemRepository : CrudRepository<ForumItem, Long>