package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.user.model.User
import javax.transaction.Transactional

@Repository
@Transactional
interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String?): User?
    fun findByName(name: String?): User?
    fun findByBoards(board: Board): List<User>
}
