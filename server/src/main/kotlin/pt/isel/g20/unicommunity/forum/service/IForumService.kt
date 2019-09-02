package pt.isel.g20.unicommunity.forum.service

import pt.isel.g20.unicommunity.common.NotFoundBoardException
import pt.isel.g20.unicommunity.common.NotFoundForumException
import pt.isel.g20.unicommunity.forum.model.Forum
import pt.isel.g20.unicommunity.user.model.User

interface IForumService {
    @Throws(NotFoundForumException::class, NotFoundBoardException::class)
    fun getForumById(boardId: Long): Forum

    @Throws(NotFoundBoardException::class)
    fun createForum(boardId: Long): Forum

    @Throws(NotFoundForumException::class, NotFoundBoardException::class)
    fun editForum(user: User, boardId: Long): Forum

    @Throws(NotFoundForumException::class, NotFoundBoardException::class)
    fun deleteForum(user: User, boardId: Long): Forum
}