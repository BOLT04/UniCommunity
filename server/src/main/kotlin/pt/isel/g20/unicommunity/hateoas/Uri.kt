package pt.isel.g20.unicommunity.hateoas

import org.springframework.web.util.UriTemplate
import java.net.URI

object Uri {
    const val LINK_RELATIONS_ROUTE = "/rels"

// Navigation
    const val NAVIGATION_ROUTE = "/"

//USERS

    //Paths
    const val USERS_ROUTE = "/users"
    const val SINGLE_USER_ROUTE = "$USERS_ROUTE/{userId}"

    //Templates
    private val singleUserTemplate = UriTemplate(SINGLE_USER_ROUTE)

    //Getters
    fun forAllUsers() =
            URI(USERS_ROUTE)

    fun forSingleUser(userId: Long) =
            singleUserTemplate.expand(userId)


//BOARDS

    //Paths
    const val BOARDS_ROUTE = "/boards"
    const val SINGLE_BOARD_ROUTE = "$BOARDS_ROUTE/{boardId}"

    //Templates
    private val singleBoardTemplate = UriTemplate(SINGLE_BOARD_ROUTE)

    //Getters
    fun forAllBoards() =
            URI(BOARDS_ROUTE)

    fun forSingleBoard(boardId: Long) =
            singleBoardTemplate.expand(boardId)


//BLACKBOARDS

    //Paths
    const val BLACKBOARDS_ROUTE = "$SINGLE_BOARD_ROUTE/blackboards"
    const val SINGLE_BLACKBOARD_ROUTE = "$BLACKBOARDS_ROUTE/{bbId}"

    //Templates
    private val singleBlackboardTemplate = UriTemplate(SINGLE_BLACKBOARD_ROUTE)
    private val multipleBlackboardTemplate = UriTemplate(BLACKBOARDS_ROUTE)

    //Getters
    fun forAllBlackboards(boardId: Long) =
            multipleBlackboardTemplate.expand(boardId)

    fun forSingleBlackboard(boardId: Long, bbId: Long) =
            singleBlackboardTemplate.expand(boardId, bbId)


//BLACKBOARDITEMS

    //Paths
    const val BLACKBOARDITEMS_ROUTE = "$SINGLE_BLACKBOARD_ROUTE/submissions"
    const val SINGLE_BLACKBOARDITEM_ROUTE = "$BLACKBOARDITEMS_ROUTE/{itemId}"

    //Templates
    private val singleBlackboardItemTemplate = UriTemplate(SINGLE_BLACKBOARDITEM_ROUTE)
    private val multipleBlackboardItemTemplate = UriTemplate(BLACKBOARDITEMS_ROUTE)

    //Getters
    fun forAllBlackboardItems(boardId: Long, bbId: Long) =
            multipleBlackboardItemTemplate.expand(boardId, bbId)

    fun forSingleBlackboardItem(boardId: Long, bbId: Long, itemId: Long) =
            singleBlackboardItemTemplate.expand(boardId, bbId, itemId)


//FORUM

    //Paths
    const val FORUM_ROUTE = "$SINGLE_BOARD_ROUTE/forum"

    //Templates
    private val singleForumTemplate = UriTemplate(FORUM_ROUTE)

    //Getters
    fun forSingleForum(boardId: Long) =
            singleForumTemplate.expand(boardId)


//FORUMITEMS

    //Paths
    const val FORUMITEMS_ROUTE = "$SINGLE_BOARD_ROUTE/forum/posts"
    const val SINGLE_FORUMITEM_ROUTE = "$FORUMITEMS_ROUTE/{forumItemId}"

    //Templates
    private val singleForumItemTemplate = UriTemplate(SINGLE_FORUMITEM_ROUTE)
    private val multipleForumItemsTemplate = UriTemplate(FORUMITEMS_ROUTE)

    //Getters
    fun forAllForumItems(boardId: Long) =
            multipleForumItemsTemplate.expand(boardId)

    fun forSingleForumItem(boardId: Long, forumItemId: Long) =
            singleForumItemTemplate.expand(boardId, forumItemId)

// COMMENTS

    //Paths
    const val COMMENTS_ROUTE = "$SINGLE_FORUMITEM_ROUTE/comments"
    const val SINGLE_COMMENT_ROUTE = "$COMMENTS_ROUTE/{commentId}"

    //Templates
    private val singleCommentTemplate = UriTemplate(SINGLE_COMMENT_ROUTE)
    private val multipleCommentsTemplate = UriTemplate(COMMENTS_ROUTE)

    //Getters
    fun forAllComments(boardId: Long, forumItemId: Long) =
            multipleCommentsTemplate.expand(boardId, forumItemId)

    fun forSingleComment(boardId: Long, forumItemId: Long, commentId: Long) =
            singleCommentTemplate.expand(boardId, forumItemId, commentId)


//TEMPLATES

    //Paths
    const val TEMPLATES_ROUTE = "/templates"
    const val SINGLE_TEMPLATE_ROUTE = "$TEMPLATES_ROUTE/{templateId}"

    //Templates
    private val singleTemplateTemplate = UriTemplate(SINGLE_TEMPLATE_ROUTE)

    //Getters
    fun forAllTemplates() =
            URI(TEMPLATES_ROUTE)

    fun forSingleTemplate(templateId: Long) =
            singleTemplateTemplate.expand(templateId)
}