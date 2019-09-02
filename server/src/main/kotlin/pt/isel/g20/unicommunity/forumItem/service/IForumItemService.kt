package pt.isel.g20.unicommunity.forumItem.service

import pt.isel.g20.unicommunity.common.NotFoundBoardException
import pt.isel.g20.unicommunity.common.NotFoundForumException
import pt.isel.g20.unicommunity.common.NotFoundForumItemException
import pt.isel.g20.unicommunity.common.NotFoundUserException
import pt.isel.g20.unicommunity.forumItem.model.ForumItem
import pt.isel.g20.unicommunity.user.model.User


interface IForumItemService {
    @Throws(NotFoundBoardException::class, NotFoundForumException::class)
    fun getAllForumItems(boardId: Long) : Iterable<ForumItem>

    @Throws(NotFoundForumItemException::class, NotFoundBoardException::class)
    fun getForumItemById(boardId: Long, forumItemId: Long): ForumItem

    @Throws(NotFoundBoardException::class, NotFoundForumException::class, NotFoundUserException::class)
    fun createForumItem(boardId: Long, authorId: Long, name: String, content: String, anonymousPost: Boolean): ForumItem

    @Throws(NotFoundForumItemException::class, NotFoundBoardException::class)
    fun editForumItem(user: User, boardId: Long, forumItemId: Long, name: String?, content: String?): ForumItem

    @Throws(NotFoundForumItemException::class, NotFoundBoardException::class)
    fun deleteForumItem(user: User, boardId: Long, forumItemId: Long): ForumItem
}