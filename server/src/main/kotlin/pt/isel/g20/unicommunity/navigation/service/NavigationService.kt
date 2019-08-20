package pt.isel.g20.unicommunity.navigation.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.board.model.Board
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.UserRepository

@Service
class NavigationService(
        val boardsRepo: BoardRepository,
        val usersRepo: UserRepository
) : INavigationService {
    override fun getMyBoards(userId: Long): Iterable<Board> {
        val user = usersRepo.findByIdOrNull(userId) ?: throw NotFoundUserException()
        return boardsRepo.findBoardsByMembers(user).asIterable()
    }
}