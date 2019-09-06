package pt.isel.g20.unicommunity.forum.service

import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forum.model.Forum

interface IForumService {
    @Throws(NotFoundForumException::class, NotFoundBoardException::class)
    fun getForumById(boardId: Long): Forum

    @Throws(NotFoundBoardException::class)
    fun createForum(boardId: Long, allowImagePosts: Boolean?): Forum

    @Throws(NotFoundForumException::class, NotFoundBoardException::class)
    fun editForum(boardId: Long, allowImagePosts: Boolean?): Forum

    @Throws(NotFoundForumException::class, NotFoundBoardException::class)
    fun deleteForum(boardId: Long): Forum
}