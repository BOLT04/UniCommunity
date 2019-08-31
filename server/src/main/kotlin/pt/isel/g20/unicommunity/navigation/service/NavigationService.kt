package pt.isel.g20.unicommunity.navigation.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.repository.UserRepository
import pt.isel.g20.unicommunity.repository.UsersBoardsRepository

@Service
class NavigationService(
        val usersRepo: UserRepository,
        val usersBoardsRepo: UsersBoardsRepository
) : INavigationService {
    override fun getMyBoards(userId: Long): Iterable<Board> {
        usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()
        val userBoards = usersBoardsRepo.findByUserId(userId)
        return userBoards.map { it.board }
    }
}