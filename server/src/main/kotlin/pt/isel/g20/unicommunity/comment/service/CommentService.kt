package pt.isel.g20.unicommunity.comment.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.comment.exception.NotFoundCommentException
import pt.isel.g20.unicommunity.comment.model.Comment
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.repository.BoardRepository
import pt.isel.g20.unicommunity.repository.CommentRepository
import pt.isel.g20.unicommunity.repository.ForumItemRepository
import pt.isel.g20.unicommunity.repository.ForumRepository

@Service
class CommentService(
        val commentsRepo: CommentRepository,
        val forumItemsRepo: ForumItemRepository,
        val forumsRepo: ForumRepository,
        val boardsRepo: BoardRepository
) : ICommentService{
    override fun getAllComments(boardId: Long, forumItemId: Long): Iterable<Comment> {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        return forumItemsRepo.findByIdOrNull(forumItemId)?.comments ?: throw NotFoundForumItemException()
    }

    override fun getCommentById(boardId: Long, forumItemId: Long, commentId: Long): Comment {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        val forumItem = forumItemsRepo.findByIdOrNull(forumItemId) ?: throw NotFoundForumItemException()

        return forumItem.comments.find { it.id == commentId } ?: throw NotFoundCommentException()
    }

    override fun createComment(boardId: Long, forumItemId: Long, content: String): Comment {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        val forumItem = forumItemsRepo.findByIdOrNull(forumItemId) ?: throw NotFoundForumItemException()

        val comment = Comment(forumItem, content)

        forumItem.comments.add(comment)

        val newComment = commentsRepo.save(comment)
        forumItemsRepo.save(forumItem)

        return newComment
    }

    override fun editComment(boardId: Long, forumItemId: Long, commentId: Long, content: String?): Comment {
        val comment = getCommentById(boardId, forumItemId, commentId)

        if(content != null)
            comment.content = content

        return commentsRepo.save(comment)
    }

    override fun deleteComment(boardId: Long, forumItemId: Long, commentId: Long): Comment {
        val comment = getCommentById(boardId, forumItemId, commentId)
        commentsRepo.delete(comment)
        return comment
    }


}