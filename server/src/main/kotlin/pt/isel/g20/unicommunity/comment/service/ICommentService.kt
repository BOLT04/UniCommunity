package pt.isel.g20.unicommunity.comment.service

import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.comment.exception.NotFoundCommentException
import pt.isel.g20.unicommunity.comment.model.Comment
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException


interface ICommentService {
    @Throws(
            NotFoundBoardException::class,
            NotFoundForumItemException::class,
            NotFoundForumException::class
    )
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
            NotFoundForumException::class
    )
    fun createComment(boardId: Long, forumItemId: Long, content: String): Comment

    @Throws(
            NotFoundCommentException::class,
            NotFoundBoardException::class,
            NotFoundForumItemException::class,
            NotFoundForumException::class
    )
    fun editComment(boardId: Long, forumItemId: Long, commentId: Long, content: String?): Comment

    @Throws(
            NotFoundCommentException::class,
            NotFoundBoardException::class,
            NotFoundForumItemException::class,
            NotFoundForumException::class
    )
    fun deleteComment(boardId: Long, forumItemId: Long, commentId: Long): Comment
}