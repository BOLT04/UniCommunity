package pt.isel.g20.unicommunity.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.board.model.Board
import javax.transaction.Transactional

@Repository
@Transactional
interface BoardRepository : PagingAndSortingRepository<Board, Long> {
    fun findByName(name: String?): List<Board>?
}