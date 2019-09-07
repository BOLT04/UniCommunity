package pt.isel.g20.unicommunity.features.comment.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.comment.model.Comment
import pt.isel.g20.unicommunity.features.user.model.User
import pt.isel.g20.unicommunity.repository.*

@Service
class CommentService(
        val commentsRepo: CommentRepository,
        val forumItemsRepo: ForumItemRepository,
        val forumsRepo: ForumRepository,
        val boardsRepo: BoardRepository,
        val usersRepo: UserRepository
) {
    fun getAllComments(boardId: Long, forumItemId: Long): Iterable<Comment> {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        forumItemsRepo.findByIdOrNull(forumItemId) ?: throw NotFoundForumItemException()
        return commentsRepo.findByForumItemIdOrderByCreatedAtDesc(forumItemId).asIterable()
    }

    fun getCommentById(boardId: Long, forumItemId: Long, commentId: Long): Comment {
        boardsRepo.findByIdOrNull(boardId) ?: throw NotFoundBoardException()
        forumsRepo.findByIdOrNull(boardId) ?: throw NotFoundForumException()
        forumItemsRepo.findByIdOrNull(forumItemId) ?: throw NotFoundForumItemException()
        return commentsRepo.findByForumItemIdAndId(forumItemId, commentId) ?: throw NotFoundCommentException()
    }

    fun createComment(boardId: Long, forumItemId: Long, authorId: Long, content: String, anonymous: Boolean): Comment {
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

    fun editComment(user: User, boardId: Long, forumItemId: Long, commentId: Long, content: String?): Comment {
        val comment = getCommentById(boardId, forumItemId, commentId)
        if(user.id != comment.author.id && user.role != ADMIN) throw ForbiddenException()
        if(content != null)
            comment.content = content

        return commentsRepo.save(comment)
    }

    fun deleteComment(user: User, boardId: Long, forumItemId: Long, commentId: Long): Comment {
        val comment = getCommentById(boardId, forumItemId, commentId)
        if(user.id != comment.author.id && user.role != ADMIN) throw ForbiddenException()

        //Hibernate.initialize(comment.reports)

        commentsRepo.delete(comment)
        return comment
    }


}