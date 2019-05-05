package pt.isel.g20.unicommunity.forum.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forum.model.Forum
import pt.isel.g20.unicommunity.repository.ForumRepository
import pt.isel.g20.unicommunity.repository.BoardRepository

@Service
class ForumService(
        val forumsRepo: ForumRepository,
        val boardsRepo: BoardRepository
) : IForumService {
    override fun getForumById(boardId: Long) =
            forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()

    override fun createForum(
            boardId: Long,
            allowImagePosts: Boolean?
    ): Forum {
        val board = boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()

        val forum = Forum(boardId, board)
        val newForum = forumsRepo.save(forum)

        board.forum = newForum
        boardsRepo.save(board)

        return newForum
    }

    override fun editForum(boardId: Long, allowImagePosts: Boolean?): Forum {
        val forum = getForumById(boardId)

        if(allowImagePosts != null)
            forum.isAllowImagePosts = allowImagePosts

        return forumsRepo.save(forum)
    }

    override fun deleteForum(boardId: Long): Forum {
        val forum = getForumById(boardId)
        forumsRepo.delete(forum)
        return forum
    }

}