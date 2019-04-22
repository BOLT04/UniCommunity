package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.forum.model.Forum
import javax.transaction.Transactional

@Repository
@Transactional
interface ForumRepository : CrudRepository<Forum, Long>