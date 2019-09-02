package pt.isel.g20.unicommunity.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.usersBoards.UsersBoards
import javax.transaction.Transactional

@Repository
@Transactional
interface BoardRepository : PagingAndSortingRepository<Board, Long> {
    fun findByName(name: String?): List<Board>
    fun findByUsersBoardsIn(usersBoards: List<UsersBoards>, pageable: Pageable): Page<Board>
    fun findByActiveTrue(pageable: Pageable): Page<Board>
}