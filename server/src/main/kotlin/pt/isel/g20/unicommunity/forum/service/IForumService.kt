package pt.isel.g20.unicommunity.forum.service

import pt.isel.g20.unicommunity.common.NotFoundBoardException
import pt.isel.g20.unicommunity.common.NotFoundForumException
import pt.isel.g20.unicommunity.forum.model.Forum

interface IForumService {
    @Throws(NotFoundForumException::class, NotFoundBoardException::class)
    fun getForumById(boardId: Long): Forum

    @Throws(NotFoundBoardException::class)
    fun createForum(boardId: Long): Forum

    @Throws(NotFoundForumException::class, NotFoundBoardException::class)
    fun editForum(boardId: Long): Forum

    @Throws(NotFoundForumException::class, NotFoundBoardException::class)
    fun deleteForum(boardId: Long): Forum
}