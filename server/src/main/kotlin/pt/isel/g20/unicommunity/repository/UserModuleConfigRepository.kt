package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.fcm.UserModuleConfig
import javax.transaction.Transactional

@Repository
@Transactional
interface UserModuleConfigRepository : PagingAndSortingRepository<UserModuleConfig, Long> {
    fun findByUserIdAndBlackboardId(userId: Long, bbId: Long): UserModuleConfig
}