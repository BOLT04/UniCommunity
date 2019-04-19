package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import pt.isel.g20.unicommunity.forum.model.Forum

interface ForumRepository : CrudRepository<Forum, Long>