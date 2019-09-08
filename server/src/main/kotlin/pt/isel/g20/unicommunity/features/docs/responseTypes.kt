package pt.isel.g20.unicommunity.features.docs

import pt.isel.g20.unicommunity.common.*

class ListBoardsHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_MULTIPLE_BOARDS)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "List of all the boards",
                        method = "get"
                )
        )
)

class CreateBoardHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.CREATE_BOARD)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Create a board",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "description",
                                        prompt = "Description",
                                        required = true
                                ),
                                Property(
                                        name = "templateId",
                                        prompt = "The chosen template's id",
                                        required = false
                                ),
                                Property(
                                        name = "blackboardNames",
                                        prompt = "List of names to be attributed to the blackboards",
                                        required = false
                                ),
                                Property(
                                        name = "hasForum",
                                        prompt = "Tells whether the board has a forum",
                                        required = false
                                )
                        )
                )
        )
)

class GetBoardHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_BOARD)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get a specific board",
                        method = "get"
                )
        )
)

class EditBoardHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.EDIT_BOARD)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Edit board",
                        method = "put",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "description",
                                        prompt = "Description",
                                        required = true
                                )
                        )
                )
        )
)

class DeleteBoardHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.DELETE_BOARD)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Delete a board",
                        method = "delete",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class ListBlackboardsHalFormsResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.GET_MULTIPLE_BLACKBOARDS)
    ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "List blackboards of board",
                        method = "get"
                )
        )
)

class CreateBlackboardHalFormsResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.CREATE_BLACKBOARD)
    ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Create a blackboard on a board",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "description",
                                        prompt = "Description",
                                        required = true
                                ),
                                Property(
                                        name = "notificationLevel",
                                        prompt = "Notification Level",
                                        required = true
                                )
                        )
                )
        )
)

class GetBlackboardHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_BLACKBOARD)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get a specific blackboard from a board",
                        method = "get"
                )
        )
)

class EditBlackboardHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.EDIT_BLACKBOARD)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Edit blackboard",
                        method = "put",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "description",
                                        prompt = "Description",
                                        required = true
                                ),
                                Property(
                                        name = "notificationLevel",
                                        prompt = "Notification Level",
                                        required = true
                                )
                        )
                )
        )
)

class DeleteBlackboardHalFormsResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.DELETE_BLACKBOARD)
    ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Delete a blackboard from a board",
                        method = "delete",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class ListBlackboardItemsHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_MULTIPLE_BLACKBOARDITEMS)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "List Blackboard items of a blackboard",
                        method = "get"
                )
        )
)

class CreateBlackboardItemHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.CREATE_BLACKBOARDITEM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Create a Blackboard item on a blackboard",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "content",
                                        prompt = "Content",
                                        required = true
                                )
                        )
                )
        )
)

class GetBlackboardItemHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_BLACKBOARDITEM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get a specific Blackboard item from a blackboard",
                        method = "get"
                )
        )
)

class EditBlackboardItemHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.EDIT_BLACKBOARDITEM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Edit Blackboard item",
                        method = "put",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "content",
                                        prompt = "Description",
                                        required = true
                                )
                        )
                )
        )
)

class DeleteBlackboardItemHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.DELETE_BLACKBOARDITEM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Delete a Blackboard item from a blackboard",
                        method = "delete",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class ListUsersHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_MULTIPLE_USERS)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "List all users",
                        method = "get"
                )
        )
)

class CreateUserHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.CREATE_USER)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Create an user",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "email",
                                        prompt = "Email",
                                        required = true
                                ),
                                Property(
                                        name = "password",
                                        prompt = "Password",
                                        required = true
                                ),
								Property(
                                        name = "role",
                                        prompt = "The role of the user",
                                        required = true
                                ),
                                Property(
                                        name = "githubId",
                                        prompt = "Github ID",
                                        required = false
                                )
                        )
                )
        )
)

class GetUserHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_USER)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get a specific user",
                        method = "get"
                )
        )
)

class EditUserHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.EDIT_USER)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Edit user",
                        method = "put",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "email",
                                        prompt = "Email",
                                        required = true
                                ),
                                Property(
                                        name = "password",
                                        prompt = "Password",
                                        required = true
                                ),
                                Property(
                                        name = "githubId",
                                        prompt = "Github ID",
                                        required = false
                                )
                        )
                )
        )
)

class DeleteUserHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.DELETE_USER)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Delete user",
                        method = "delete",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class ListTemplatesHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_MULTIPLE_TEMPLATES)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "List all Templates",
                        method = "get"
                )
        )
)

class CreateTemplateHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.CREATE_TEMPLATE)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Create an Template",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "hasForum",
                                        prompt = "Tells if the board created with this template will have a forum",
                                        required = true
                                ),
                                Property(
                                        name = "blackboardNames",
                                        prompt = "The list of names to be attributed to the blackboards",
                                        required = true
                                )
                        )
                )
        )
)

class GetTemplateHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_TEMPLATE)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get a specific Template",
                        method = "get"
                )
        )
)

class EditTemplateHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.EDIT_TEMPLATE)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Edit Template",
                        method = "put",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "hasForum",
                                        prompt = "Tells if the board created with this template will have a forum",
                                        required = true
                                ),
                                Property(
                                        name = "blackboardNames",
                                        prompt = "The list of names to be attributed to the blackboards",
                                        required = true
                                )
                        )
                )
        )
)

class DeleteTemplateHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.DELETE_TEMPLATE)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Delete Template",
                        method = "delete",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class CreateForumHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.CREATE_FORUM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Create an Forum",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class GetForumHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_FORUM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get a specific Forum",
                        method = "get"
                )
        )
)

class EditForumHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.EDIT_FORUM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Edit Forum",
                        method = "put",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class DeleteForumHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.DELETE_FORUM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Delete Forum",
                        method = "delete",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class ListForumItemsHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_MULTIPLE_FORUMITEMS)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "List all Forum items",
                        method = "get"
                )
        )
)

class CreateForumItemHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.CREATE_FORUMITEM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Create a Forum item",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "content",
                                        prompt = "Content",
                                        required = true
                                ),
                                Property(
                                        name = "anonymousPost",
                                        prompt = "Tells whether the post is anonymous",
                                        required = true
                                )
                        )
                )
        )
)

class GetForumItemHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_FORUMITEM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get a specific Forum item",
                        method = "get"
                )
        )
)

class EditForumItemHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.EDIT_FORUMITEM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Edit Forum item",
                        method = "put",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "name",
                                        prompt = "Name",
                                        required = true
                                ),
                                Property(
                                        name = "content",
                                        prompt = "Content",
                                        required = true
                                ),
                                Property(
                                        name = "anonymousPost",
                                        prompt = "Tells whether the post is anonymous",
                                        required = true
                                )
                        )
                )
        )
)

class DeleteForumItemHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.DELETE_FORUMITEM)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Delete Forum item",
                        method = "delete",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class ListCommentsHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_MULTIPLE_COMMENTS)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "List all comments from a forum item",
                        method = "get"
                )
        )
)

class CreateCommentHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.CREATE_COMMENT)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Create a comment on a forum item",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "content",
                                        prompt = "Content",
                                        required = true
                                ),
                                Property(
                                        name = "anonymous",
                                        prompt = "Tells whether the comment is marked as anonymous",
                                        required = true
                                )
                        )
                )
        )
)

class GetCommentHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_COMMENT)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get a specific comment from a forum item",
                        method = "get"
                )
        )
)

class EditCommentHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.EDIT_COMMENT)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Edit comment",
                        method = "put",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "content",
                                        prompt = "Content",
                                        required = true
                                ),
                                Property(
                                        name = "anonymous",
                                        prompt = "Tells whether the comment is marked as anonymous",
                                        required = true
                                )
                        )
                )
        )
)

class DeleteCommentHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.DELETE_COMMENT)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Delete comment",
                        method = "delete",
                        contentType = APPLICATION_JSON,
                        properties = listOf()
                )
        )
)

class SubscribeHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.SUBSCRIBE)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Subscribe to a board",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "fcmToken",
                                        prompt = "Token used to subscribe to fcm topic. Used by web app only",
                                        required = false
                                )
                        )
                )
        )
)

class UnsubscribeHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.UNSUBSCRIBE)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Unsubscribe from a board",
                        method = "delete"
                )
        )
)

class GetBoardMembersHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_BOARD_MEMBERS)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get all the members of a board",
                        method = "get"
                )
        )
)

class GetUserBlackboardsSettingsHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_USER_BLACKBOARDS_SETTINGS)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get the notification settings of all blackboards of the logged user",
                        method = "get"
                )
        )
)

class GetUserBlackboardSettingsHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_USERS_BLACKBOARD)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get the notification settings of a specific blackboard of the logged user",
                        method = "get"
                )
        )
)

class GetMyBoardsHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.MY_BOARDS)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get the boards of the logged user",
                        method = "get"
                )
        )
)

class GetHomeHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.HOME)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get home resources",
                        method = "get"
                )
        )
)

class GetNavigationHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.NAVIGATION)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get navigation resources",
                        method = "get"
                )
        )
)

class ListReportsHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_MULTIPLE_REPORTS)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "List of all the reports",
                        method = "get"
                )
        )
)

class CreateReportHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.CREATE_REPORT)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Create a report",
                        method = "post",
                        contentType = APPLICATION_JSON,
                        properties = listOf(
                                Property(
                                        name = "userId",
                                        prompt = "The id of the author of the forumItem/comment",
                                        required = true
                                ),
                                Property(
                                        name = "forumItemId",
                                        prompt = "The id of the forumItem being reported. If this property has a value - commentId should be null",
                                        required = false
                                ),
                                Property(
                                        name = "commentId",
                                        prompt = "The id of the comment being reported. . If this property has a value - forumItemId should be null",
                                        required = false
                                )
                        )
                )
        )
)

class GetReportHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.GET_SINGLE_REPORT)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Get a specific report",
                        method = "get"
                )
        )
)

class DeleteReportHalFormsResponse : HalFormsObject(
        mapOf(
                "self" to Link(Rels.DELETE_REPORT)
        ),
        HalFormsTemplateObject(
                HalFormsTemplate(
                        title = "Delete a specific report",
                        method = "delete"
                )
        )
)