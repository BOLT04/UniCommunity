package pt.isel.g20.unicommunity.comment.service

import pt.isel.g20.unicommunity.comment.model.Comment
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.user.model.User


interface ICommentService {
    @Throws(NotFoundBoardException::class, NotFoundForumItemException::class, NotFoundForumException::class)
    fun getAllComments(boardId: Long, forumItemId: Long) : Iterable<Comment>

    @Throws(
            NotFoundCommentException::class,
            NotFoundBoardException::class,
            NotFoundForumItemException::class,
            NotFoundForumException::class
    )
    fun getCommentById(boardId: Long, forumItemId: Long, commentId: Long): Comment

    @Throws(
            NotFoundBoardException::class,
            NotFoundForumItemException::class,
            NotFoundForumException::class,
            NotFoundUserException::class
    )
    fun createComment(boardId: Long, forumItemId: Long, authorId: Long, content: String, anonymous: Boolean): Comment

    @Throws(
            NotFoundCommentException::class,
            NotFoundBoardException::class,
            NotFoundForumItemException::class,
            NotFoundForumException::class
    )
    fun editComment(user: User, boardId: Long, forumItemId: Long, commentId: Long, content: String?): Comment

    @Throws(
            NotFoundCommentException::class,
            NotFoundBoardException::class,
            NotFoundForumItemException::class,
            NotFoundForumException::class
    )
    fun deleteComment(user: User, boardId: Long, forumItemId: Long, commentId: Long): Comment
}