package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import pt.isel.g20.unicommunity.usersBoards.UsersBoards

@Repository
@Transactional
interface UsersBoardsRepository : CrudRepository<UsersBoards, Long> {
    fun findByUserIdAndBoardId(userId: Long, blackboardId: Long): UsersBoards?
    fun findByUserId(userId: Long): List<UsersBoards>
    fun findByBoardId(boardId: Long): List<UsersBoards>
}