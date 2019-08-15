package pt.isel.g20.unicommunity.forumItem.service

import pt.isel.g20.unicommunity.board.exception.NotFoundBoardException
import pt.isel.g20.unicommunity.forum.exception.NotFoundForumException
import pt.isel.g20.unicommunity.forum.model.Forum
import pt.isel.g20.unicommunity.forumItem.exception.NotFoundForumItemException
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.user.exception.NotFoundUserException


interface IForumItemService {
    @Throws(NotFoundBoardException::class, NotFoundForumException::class)
    fun getAllForumItems(boardId: Long) : Iterable<ForumItem>

    @Throws(NotFoundForumItemException::class, NotFoundBoardException::class)
    fun getForumItemById(boardId: Long, forumItemId: Long): ForumItem

    @Throws(NotFoundBoardException::class, NotFoundForumException::class, NotFoundUserException::class)
    fun createForumItem(boardId: Long, authorId: Long, name: String, content: String, anonymousPost: Boolean): ForumItem

    @Throws(NotFoundForumItemException::class, NotFoundBoardException::class)
    fun editForumItem(boardId: Long, forumItemId: Long, name: String?, content: String?): ForumItem

    @Throws(NotFoundForumItemException::class, NotFoundBoardException::class)
    fun deleteForumItem(boardId: Long, forumItemId: Long): ForumItem
}