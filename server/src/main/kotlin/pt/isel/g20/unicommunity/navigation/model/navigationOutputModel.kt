package pt.isel.g20.unicommunity.navigation.model

import pt.isel.g20.unicommunity.common.ADMIN
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.HalObject
import pt.isel.g20.unicommunity.hateoas.Link
import pt.isel.g20.unicommunity.user.model.User

class NavigationResponse(user: User): HalObject(_links = mutableMapOf()) {
    init {
        _links!!.putAll(sequenceOf(
                "self" to Link(Uri.NAVIGATION_ROUTE),
                Rels.LOGOUT to Link("/logout"),
                Rels.USER_PROFILE to Link(Uri.forSingleUserText(user.id)),
                Rels.MY_BOARDS to Link("/myBoards"),
                Rels.GET_MULTIPLE_BOARDS to Link(Uri.forAllBoards()),
                Rels.CREATE_BOARD to Link(Uri.forAllBoards())
        ))

        if(user.role == ADMIN)
            _links!!.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_REPORTS to Link(Uri.forAllReports())
            ))

    }
}

class HomeResponse : HalObject(
    _links=  mutableMapOf(
            "self" to Link(Uri.HOME_ROUTE),
            Rels.NAVIGATION to Link(Uri.NAVIGATION_ROUTE),
            Rels.LOGIN to Link(Uri.LOGIN_ROUTE)
    ),
    _embedded = mutableMapOf(
            Rels.GET_MULTIPLE_BOARDS to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.BOARDS_ROUTE))),
            Rels.GET_MULTIPLE_BLACKBOARDS to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.BLACKBOARDS_ROUTE))),
            Rels.GET_MULTIPLE_BLACKBOARDITEMS to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.BLACKBOARDITEMS_ROUTE))),
            Rels.GET_MULTIPLE_FORUMITEMS to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.FORUMITEMS_ROUTE))),
            Rels.GET_MULTIPLE_COMMENTS to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.COMMENTS_ROUTE))),
            Rels.GET_MULTIPLE_TEMPLATES to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.TEMPLATES_ROUTE))),
            Rels.GET_MULTIPLE_USERS to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.USERS_ROUTE))),
            Rels.GET_MULTIPLE_REPORTS to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.REPORTS_ROUTE))),
            Rels.GET_SINGLE_BOARD to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_BOARD_ROUTE))),
            Rels.GET_SINGLE_BLACKBOARD to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_BLACKBOARD_ROUTE))),
            Rels.GET_SINGLE_BLACKBOARDITEM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_BLACKBOARDITEM_ROUTE))),
            Rels.GET_SINGLE_FORUM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.FORUM_ROUTE))),
            Rels.GET_SINGLE_FORUMITEM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_FORUMITEM_ROUTE))),
            Rels.GET_SINGLE_COMMENT to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_COMMENT_ROUTE))),
            Rels.GET_SINGLE_TEMPLATE to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_TEMPLATE_ROUTE))),
            Rels.GET_SINGLE_USER to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_USER_ROUTE))),
            Rels.GET_SINGLE_REPORT to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_REPORT_ROUTE))),
            Rels.CREATE_BOARD to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.BOARDS_ROUTE))),
            Rels.CREATE_BLACKBOARD to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.BLACKBOARDS_ROUTE))),
            Rels.CREATE_BLACKBOARDITEM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.BLACKBOARDITEMS_ROUTE))),
            Rels.CREATE_FORUM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.FORUM_ROUTE))),
            Rels.CREATE_FORUMITEM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.FORUMITEMS_ROUTE))),
            Rels.CREATE_COMMENT to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.COMMENTS_ROUTE))),
            Rels.CREATE_TEMPLATE to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.TEMPLATES_ROUTE))),
            Rels.CREATE_USER to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.USERS_ROUTE))),
            Rels.CREATE_REPORT to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.REPORTS_ROUTE))),
            Rels.EDIT_BOARD to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_BOARD_ROUTE))),
            Rels.EDIT_BLACKBOARD to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_BLACKBOARD_ROUTE))),
            Rels.EDIT_BLACKBOARDITEM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_BLACKBOARDITEM_ROUTE))),
            Rels.EDIT_FORUM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.FORUM_ROUTE))),
            Rels.EDIT_FORUMITEM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_FORUMITEM_ROUTE))),
            Rels.EDIT_COMMENT to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_COMMENT_ROUTE))),
            Rels.EDIT_TEMPLATE to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_TEMPLATE_ROUTE))),
            Rels.EDIT_USER to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_USER_ROUTE))),
            Rels.DELETE_BOARD to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_BOARD_ROUTE))),
            Rels.DELETE_BLACKBOARD to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_BLACKBOARD_ROUTE))),
            Rels.DELETE_BLACKBOARDITEM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_BLACKBOARDITEM_ROUTE))),
            Rels.DELETE_FORUM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.FORUM_ROUTE))),
            Rels.DELETE_FORUMITEM to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_FORUMITEM_ROUTE))),
            Rels.DELETE_COMMENT to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_COMMENT_ROUTE))),
            Rels.DELETE_TEMPLATE to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_TEMPLATE_ROUTE))),
            Rels.DELETE_USER to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_USER_ROUTE))),
            Rels.GET_BOARD_MEMBERS to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.BOARD_MEMBERS))),
            Rels.SUBSCRIBE to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.BOARD_MEMBERS))),
            Rels.UNSUBSCRIBE to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.BOARD_MEMBERS))),
            Rels.GET_USER_BLACKBOARDS_SETTINGS to
                    HalObject(_links= mutableMapOf("self" to Link(Uri.SINGLE_USER_ROUTE)))
    )
)

class UnauthorizedNavigationResponse
    : HalObject(
        mutableMapOf(
                "self" to Link(Uri.NAVIGATION_ROUTE),
                //Rels.HOME to Link(Uri.HOME_ROUTE),
                Rels.LOGIN to Link(Uri.LOGIN_ROUTE)
                //Rels.LOGOUT to Link("/logout"), //TODO: for now since we don't have cookie auth, we don't need logout
        )
)