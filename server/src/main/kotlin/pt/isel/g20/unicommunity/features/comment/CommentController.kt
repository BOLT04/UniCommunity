package pt.isel.g20.unicommunity.features.comment

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.COMMENTS_ROUTE
import pt.isel.g20.unicommunity.common.Uri.SINGLE_COMMENT_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.features.comment.model.CommentDto
import pt.isel.g20.unicommunity.features.comment.model.MultipleCommentsResponse
import pt.isel.g20.unicommunity.features.comment.model.SingleCommentResponse
import pt.isel.g20.unicommunity.features.comment.model.toItemRepr
import pt.isel.g20.unicommunity.features.comment.service.CommentService
import pt.isel.g20.unicommunity.features.user.model.User

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class CommentController(private val service: CommentService) {

    @AuthorizationRequired
    @GetMapping(path = [COMMENTS_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllComments(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @SessionAttribute("user") user: User
    ) =
            okResponse(
                    CollectionObject(
                            MultipleCommentsResponse(
                                    boardId,
                                    forumItemId,
                                    service
                                            .getAllComments(boardId, forumItemId)
                                            .map { it.toItemRepr(user) }
                            )
                    )
            )

    @AuthorizationRequired
    @GetMapping(path = [SINGLE_COMMENT_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun getCommentById(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @PathVariable commentId: Long,
            @SessionAttribute("user") user: User
    ) =
            okResponse(
                    SingleCommentResponse(
                            user,
                            service.getCommentById(boardId, forumItemId, commentId)
                    )
            )

    @AuthorizationRequired
    @PostMapping(path = [COMMENTS_ROUTE], produces = [APPLICATION_HAL_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createComment(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @RequestBody commentDto: CommentDto,
            @SessionAttribute("user")user: User
    ) =
            service.createComment(
                    boardId,
                    forumItemId,
                    user.id,
                    commentDto.content,
                    commentDto.anonymous
            ).let {
                val responseBody = SingleCommentResponse(user, it)
                val newResourceHref =
                        Uri.forSingleCommentUri(
                                it.forumItem.forum.board.id,
                                it.forumItem.id,
                                it.id
                        )
                createdResponse(responseBody, newResourceHref)
            }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_COMMENT_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun editComment(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @PathVariable commentId: Long,
            @RequestBody commentDto: CommentDto,
            @SessionAttribute("user")user: User
    ) =
            okResponse(
                    SingleCommentResponse(
                            user,
                            service.editComment(
                                    user,
                                    boardId,
                                    forumItemId,
                                    commentId,
                                    commentDto.content
                            )
                    )
            )

    @AuthorizationRequired
    @DeleteMapping(path = [SINGLE_COMMENT_ROUTE], produces = [APPLICATION_HAL_JSON])
    fun deleteComment(
            @PathVariable boardId: Long,
            @PathVariable forumItemId: Long,
            @PathVariable commentId: Long,
            @SessionAttribute("user")user: User
    ) =
            okResponse(
                    SingleCommentResponse(
                            user,
                            service.deleteComment(user, boardId, forumItemId, commentId)
                    )
            )
}