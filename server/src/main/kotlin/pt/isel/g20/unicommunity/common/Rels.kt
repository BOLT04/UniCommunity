package pt.isel.g20.unicommunity.common

object Rels {
    //TODO: temporary prefix
    const val RELS_PREFIX = "http://localhost:8080/rels"

    const val NAVIGATION = "$RELS_PREFIX/nav"
    const val HOME = "$RELS_PREFIX/home"
    const val LOGIN = "$RELS_PREFIX/login"
    const val LOGOUT = "$RELS_PREFIX/logout"
    const val MY_BOARDS = "$RELS_PREFIX/myBoards"
    const val USER_PROFILE = "$RELS_PREFIX/userProfile"

    const val CREATE_BOARD = "$RELS_PREFIX/createBoard"
    const val GET_SINGLE_BOARD = "$RELS_PREFIX/getBoard"
    const val GET_MULTIPLE_BOARDS = "$RELS_PREFIX/getBoards"
    const val EDIT_BOARD = "$RELS_PREFIX/editBoard"
    const val DELETE_BOARD = "$RELS_PREFIX/deleteBoard"

    const val CREATE_FORUM = "$RELS_PREFIX/createForum"
    const val GET_SINGLE_FORUM = "$RELS_PREFIX/getForum"
    const val EDIT_FORUM = "$RELS_PREFIX/editForum"
    const val DELETE_FORUM = "$RELS_PREFIX/deleteForum"
    
    const val CREATE_FORUMITEM = "$RELS_PREFIX/createForumItem"
    const val GET_SINGLE_FORUMITEM = "$RELS_PREFIX/getForumItem"
    const val GET_MULTIPLE_FORUMITEMS = "$RELS_PREFIX/getForumItems"
    const val EDIT_FORUMITEM = "$RELS_PREFIX/editForumItem"
    const val DELETE_FORUMITEM = "$RELS_PREFIX/deleteForumItem"

    const val CREATE_COMMENT = "$RELS_PREFIX/createComment"
    const val GET_SINGLE_COMMENT = "$RELS_PREFIX/getComment"
    const val GET_MULTIPLE_COMMENTS = "$RELS_PREFIX/getComments"
    const val EDIT_COMMENT = "$RELS_PREFIX/editComment"
    const val DELETE_COMMENT = "$RELS_PREFIX/deleteComment"

    const val CREATE_BLACKBOARD = "$RELS_PREFIX/createBlackboard"
    const val GET_SINGLE_BLACKBOARD = "$RELS_PREFIX/getBlackboard"
    const val GET_MULTIPLE_BLACKBOARDS = "$RELS_PREFIX/getBlackboards"
    const val EDIT_BLACKBOARD = "$RELS_PREFIX/editBlackboard"
    const val DELETE_BLACKBOARD = "$RELS_PREFIX/deleteBlackboard"

    const val CREATE_BLACKBOARDITEM = "$RELS_PREFIX/createBlackboardItem"
    const val GET_SINGLE_BLACKBOARDITEM = "$RELS_PREFIX/getBlackboardItem"
    const val GET_MULTIPLE_BLACKBOARDITEMS = "$RELS_PREFIX/getBlackboardItems"
    const val EDIT_BLACKBOARDITEM = "$RELS_PREFIX/editBlackboardItem"
    const val DELETE_BLACKBOARDITEM = "$RELS_PREFIX/deleteBlackboardItem"

    const val CREATE_USER = "$RELS_PREFIX/createUser"
    const val GET_SINGLE_USER = "$RELS_PREFIX/getUser"
    const val GET_MULTIPLE_USERS = "$RELS_PREFIX/getUsers"
    const val EDIT_USER = "$RELS_PREFIX/editUser"
    const val DELETE_USER = "$RELS_PREFIX/deleteUser"

    const val CREATE_TEMPLATE = "$RELS_PREFIX/createTemplate"
    const val GET_SINGLE_TEMPLATE = "$RELS_PREFIX/getTemplate"
    const val GET_MULTIPLE_TEMPLATES = "$RELS_PREFIX/getTemplates"
    const val EDIT_TEMPLATE = "$RELS_PREFIX/editTemplate"
    const val DELETE_TEMPLATE = "$RELS_PREFIX/deleteTemplate"
}