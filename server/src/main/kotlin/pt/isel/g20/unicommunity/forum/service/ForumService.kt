package pt.isel.g20.unicommunity.forum.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.ADMIN
import pt.isel.g20.unicommunity.common.ForbiddenException
import pt.isel.g20.unicommunity.common.NotFoundBoardException
import pt.isel.g20.unicommunity.common.NotFoundForumException
import pt.isel.g20.unicommunity.forum.model.Forum
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.ForumRepository
import pt.isel.g20.unicommunity.user.model.User

@Service
class ForumService(
        val forumsRepo: ForumRepository,
        val boardsRepo: BoardRepository
) {
    fun getForumById(boardId: Long) =
            forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()

    fun createForum(
            boardId: Long

    ): Forum {
        val board = boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()

        val forum = Forum(board)
        forum.id = board.id
        val newForum = forumsRepo.save(forum)

        board.forum = newForum
        boardsRepo.save(board)

        return newForum
    }

    fun editForum(user: User, boardId: Long): Forum {
        val forum = getForumById(boardId)
        if(user.id != forum.board.creator.id && user.role != ADMIN) throw ForbiddenException()

        return forumsRepo.save(forum)
    }

    fun deleteForum(user: User, boardId: Long): Forum {
        val forum = getForumById(boardId)
        if(user.id != forum.board.creator.id && user.role != ADMIN) throw ForbiddenException()

        forumsRepo.delete(forum)
        return forum
    }

}