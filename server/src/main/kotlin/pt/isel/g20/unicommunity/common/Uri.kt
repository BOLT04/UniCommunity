package pt.isel.g20.unicommunity.common

import org.springframework.web.util.UriTemplate
import java.net.URI

object Uri {

    // Navigation
    const val NAVIGATION_ROUTE = "/navigation"
    const val HOME_ROUTE = "/"
    const val MY_BOARDS = "/myBoards"

    const val REPORTS_ROUTE = "/reports"
    const val SINGLE_REPORT_ROUTE = "$REPORTS_ROUTE/{reportId}"


    fun forMyBoards() =
            URI(MY_BOARDS).toString()
    //Templates
    private val singleReportTemplate = UriTemplate(SINGLE_REPORT_ROUTE)

    //Getters
    fun forAllReports() =
            URI(REPORTS_ROUTE).toString()

    fun forSingleReportUri(reportId: Long) =
            singleReportTemplate.expand(reportId)

    fun forSingleReportText(reportId: Long) =
            Uri.forSingleReportUri(reportId).toString()


// Auth
    const val LOGIN_ROUTE = "/signin"

//USERS

    //Paths
    const val USERS_ROUTE = "/users"
    const val SINGLE_USER_ROUTE = "$USERS_ROUTE/{userId}"

    //Templates
    private val singleUserTemplate = UriTemplate(SINGLE_USER_ROUTE)

    //Getters
    fun forAllUsers() =
            URI(USERS_ROUTE).toString()

    fun forSingleUserUri(userId: Long) =
            singleUserTemplate.expand(userId)

    fun forSingleUserText(userId: Long) =
            forSingleUserUri(userId).toString()


//BOARDS

    //Paths
    const val BOARDS_ROUTE = "/boards"
    const val ACTIVE_BOARDS_ROUTE = "/activeBoards"
    const val SINGLE_BOARD_ROUTE = "$BOARDS_ROUTE/{boardId}"
    const val BOARD_MEMBERS = "$SINGLE_BOARD_ROUTE/members"

    //Templates
    private val singleBoardTemplate = UriTemplate(SINGLE_BOARD_ROUTE)
    private val boardMembersTemplate = UriTemplate(BOARD_MEMBERS)

    //Getters
    fun forAllBoards() =
            URI(BOARDS_ROUTE).toString()

    fun forAllBoards(page: Int) =
            "$BOARDS_ROUTE?page=$page"

    fun forSingleBoardUri(boardId: Long) =
            singleBoardTemplate.expand(boardId)

    fun forSingleBoardText(boardId: Long) =
            forSingleBoardUri(boardId).toString()

    fun forBoardMembers(boardId: Long) =
            boardMembersTemplate.expand(boardId).toString()

    fun forSingleBoardTemplated(): String{
        val boardPrefix = forAllBoards()
        return "$boardPrefix/{boardId}"
    }


//BLACKBOARDS

    //Paths
    const val BLACKBOARDS_ROUTE = "$SINGLE_BOARD_ROUTE/blackboards"
    const val SINGLE_BLACKBOARD_ROUTE = "$BLACKBOARDS_ROUTE/{bbId}"

    //Templates
    private val singleBlackboardTemplate = UriTemplate(SINGLE_BLACKBOARD_ROUTE)
    private val multipleBlackboardTemplate = UriTemplate(BLACKBOARDS_ROUTE)

    //Getters
    fun forAllBlackboards(boardId: Long) =
            multipleBlackboardTemplate.expand(boardId).toString()

    fun forSingleBlackboardUri(boardId: Long, bbId: Long) =
            singleBlackboardTemplate.expand(boardId, bbId)

    fun forSingleBlackboardText(boardId: Long, bbId: Long) =
            forSingleBlackboardUri(boardId, bbId).toString()


//BLACKBOARDITEMS

    //Paths
    const val BLACKBOARDITEMS_ROUTE = "$SINGLE_BLACKBOARD_ROUTE/submissions"
    const val SINGLE_BLACKBOARDITEM_ROUTE = "$BLACKBOARDITEMS_ROUTE/{itemId}"

    //Templates
    private val singleBlackboardItemTemplate = UriTemplate(SINGLE_BLACKBOARDITEM_ROUTE)
    private val multipleBlackboardItemTemplate = UriTemplate(BLACKBOARDITEMS_ROUTE)

    //Getters
    fun forAllBlackboardItems(boardId: Long, bbId: Long) =
            multipleBlackboardItemTemplate.expand(boardId, bbId).toString()

    fun forSingleBlackboardItemUri(boardId: Long, bbId: Long, itemId: Long) =
            singleBlackboardItemTemplate.expand(boardId, bbId, itemId)

    fun forSingleBlackboardItemText(boardId: Long, bbId: Long, itemId: Long) =
            forSingleBlackboardItemUri(boardId, bbId, itemId).toString()


//FORUM

    //Paths
    const val FORUM_ROUTE = "$SINGLE_BOARD_ROUTE/forum"

    //Templates
    private val singleForumTemplate = UriTemplate(FORUM_ROUTE)

    //Getters
    fun forSingleForumUri(boardId: Long) =
            singleForumTemplate.expand(boardId)

    fun forSingleForumText(boardId: Long) =
            forSingleForumUri(boardId).toString()


//FORUMITEMS

    //Paths
    const val FORUMITEMS_ROUTE = "$SINGLE_BOARD_ROUTE/forum/posts"
    const val SINGLE_FORUMITEM_ROUTE = "$FORUMITEMS_ROUTE/{forumItemId}"

    //Templates
    private val singleForumItemTemplate = UriTemplate(SINGLE_FORUMITEM_ROUTE)
    private val multipleForumItemsTemplate = UriTemplate(FORUMITEMS_ROUTE)

    //Getters
    fun forAllForumItems(boardId: Long) =
            multipleForumItemsTemplate.expand(boardId).toString()

    fun forSingleForumItemUri(boardId: Long, forumItemId: Long) =
            singleForumItemTemplate.expand(boardId, forumItemId)

    fun forSingleForumItemText(boardId: Long, forumItemId: Long) =
            forSingleForumItemUri(boardId, forumItemId).toString()

// COMMENTS

    //Paths
    const val COMMENTS_ROUTE = "$SINGLE_FORUMITEM_ROUTE/comments"
    const val SINGLE_COMMENT_ROUTE = "$COMMENTS_ROUTE/{commentId}"

    //Templates
    private val singleCommentTemplate = UriTemplate(SINGLE_COMMENT_ROUTE)
    private val multipleCommentsTemplate = UriTemplate(COMMENTS_ROUTE)

    //Getters
    fun forAllComments(boardId: Long, forumItemId: Long) =
            multipleCommentsTemplate.expand(boardId, forumItemId).toString()

    fun forSingleCommentUri(boardId: Long, forumItemId: Long, commentId: Long) =
            singleCommentTemplate.expand(boardId, forumItemId, commentId)

    fun forSingleCommentText(boardId: Long, forumItemId: Long, commentId: Long) =
            forSingleCommentUri(boardId, forumItemId, commentId).toString()


//TEMPLATES

    //Paths
    const val TEMPLATES_ROUTE = "/templates"
    const val SINGLE_TEMPLATE_ROUTE = "$TEMPLATES_ROUTE/{templateId}"

    //Templates
    private val singleTemplateTemplate = UriTemplate(SINGLE_TEMPLATE_ROUTE)

    //Getters
    fun forAllTemplates() =
            URI(TEMPLATES_ROUTE).toString()

    fun forSingleTemplateUri(templateId: Long) =
            singleTemplateTemplate.expand(templateId)

    fun forSingleTemplateText(templateId: Long) =
            forSingleTemplateUri(templateId).toString()



    const val USERS_BLACKBOARDS_ROUTE = "$SINGLE_USER_ROUTE/config"
    const val SINGLE_USERS_BLACKBOARD_ROUTE = "$USERS_BLACKBOARDS_ROUTE/{bbId}"

    //Templates
    private val singleUsersBlackboardTemplate = UriTemplate(SINGLE_USERS_BLACKBOARD_ROUTE)
    private val multipleUsersBlackboardsTemplate = UriTemplate(USERS_BLACKBOARDS_ROUTE)

    //Getters
    fun forAllUsersBlackboards(userId: Long) =
            multipleUsersBlackboardsTemplate.expand(userId).toString()

    fun forSingleUsersBlackboardUri(userId: Long, bbId: Long) =
            singleUsersBlackboardTemplate.expand(userId, bbId)

    fun forSingleUsersBlackboardText(userId: Long, bbId: Long) =
            Uri.forSingleUsersBlackboardUri(userId, bbId).toString()
}