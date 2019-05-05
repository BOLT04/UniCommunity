package pt.isel.g20.unicommunity.comment.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.comment.exception.NotFoundCommentException
import pt.isel.g20.unicommunity.comment.model.Comment
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.repository.*
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException

@Service
class CommentService(
        val commentsRepo: CommentRepository,
        val forumItemsRepo: ForumItemRepository,
        val forumsRepo: ForumRepository,
        val boardsRepo: BoardRepository,
        val usersRepo: UserRepository
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

    override fun createComment(boardId: Long, forumItemId: Long, authorId: Long, content: String, anonymous: Boolean): Comment {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        val forumItem = forumItemsRepo.findByIdOrNull(forumItemId) ?: throw NotFoundForumItemException()
        val user =usersRepo.findByIdOrNull(authorId) ?: throw NotFoundUserException()

        val comment = Comment(forumItem, user, content, anonymous)

        val newComment = commentsRepo.save(comment)
        forumItem.comments.add(newComment)
        forumItemsRepo.save(forumItem)
        user.comments.add(newComment)
        usersRepo.save(user)

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