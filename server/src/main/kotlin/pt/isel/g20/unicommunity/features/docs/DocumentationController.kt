package pt.isel.g20.unicommunity.features.docs

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.g20.unicommunity.common.APPLICATION_HAL_FORMS_JSON
import pt.isel.g20.unicommunity.common.Rels

@RestController
@RequestMapping(produces = [APPLICATION_HAL_FORMS_JSON])
class DocumentationController {

    // Since the responses of this controller are all static and never change, only compute them once.
    val listBoardsRsp = ListBoardsHalFormsResponse()
    val createBoardRsp = CreateBoardHalFormsResponse()
    val getBoardRsp = GetBoardHalFormsResponse()
    val editBoardRsp = EditBoardHalFormsResponse()
    val deleteBoardRsp = DeleteBoardHalFormsResponse()

    val listBlackboardsRsp = ListBlackboardsHalFormsResponse()
    val createBlackboardRsp = CreateBlackboardHalFormsResponse()
    val getBlackboardRsp = GetBlackboardHalFormsResponse()
    val editBlackboardRsp = EditBlackboardHalFormsResponse()
    val deleteBlackboardRsp = DeleteBlackboardHalFormsResponse()

    val listBlackboardItemsRsp = ListBlackboardItemsHalFormsResponse()
    val createBlackboardItemRsp = CreateBlackboardItemHalFormsResponse()
    val getBlackboardItemRsp = GetBlackboardItemHalFormsResponse()
    val editBlackboardItemRsp = EditBlackboardItemHalFormsResponse()
    val deleteBlackboardItemRsp = DeleteBlackboardItemHalFormsResponse()

    val listUsersRsp = ListUsersHalFormsResponse()
    val createUserRsp = CreateUserHalFormsResponse()
    val getUserRsp = GetUserHalFormsResponse()
    val editUserRsp = EditUserHalFormsResponse()
    val deleteUserRsp = DeleteUserHalFormsResponse()

    val listTemplatesRsp = ListTemplatesHalFormsResponse()
    val createTemplateRsp = CreateTemplateHalFormsResponse()
    val getTemplateRsp = GetTemplateHalFormsResponse()
    val editTemplateRsp = EditTemplateHalFormsResponse()
    val deleteTemplateRsp = DeleteTemplateHalFormsResponse()
    
    val createForumRsp = CreateForumHalFormsResponse()
    val getForumRsp = GetForumHalFormsResponse()
    val editForumRsp = EditForumHalFormsResponse()
    val deleteForumRsp = DeleteForumHalFormsResponse()

    val listForumItemsRsp = ListForumItemsHalFormsResponse()
    val createForumItemRsp = CreateForumItemHalFormsResponse()
    val getForumItemRsp = GetForumItemHalFormsResponse()
    val editForumItemRsp = EditForumItemHalFormsResponse()
    val deleteForumItemRsp = DeleteForumItemHalFormsResponse()

    val listCommentsRsp = ListCommentsHalFormsResponse()
    val createCommentRsp = CreateCommentHalFormsResponse()
    val getCommentRsp = GetCommentHalFormsResponse()
    val editCommentRsp = EditCommentHalFormsResponse()
    val deleteCommentRsp = DeleteCommentHalFormsResponse()

    val subscribeRsp = SubscribeHalFormsResponse()
    val unsubscribeRsp = UnsubscribeHalFormsResponse()
    val getBoardMembers = GetBoardMembersHalFormsResponse()
    val getUserBlackboardsSettings = GetUserBlackboardsSettingsHalFormsResponse()
    val getMyBoards = GetMyBoardsHalFormsResponse()
    val getHome = GetHomeHalFormsResponse()
    val getNavigation = GetNavigationHalFormsResponse()
    val listReportsRsp = ListReportsHalFormsResponse()
    val createReportRsp = CreateReportHalFormsResponse()
    val getReportRsp = GetReportHalFormsResponse()

    @GetMapping(path = [Rels.GET_MULTIPLE_BOARDS])
    fun getBoards() = listBoardsRsp

    @GetMapping(path = [Rels.CREATE_BOARD])
    fun createBoards() = createBoardRsp

    @GetMapping(path = [Rels.GET_SINGLE_BOARD])
    fun getBoard() = getBoardRsp

    @GetMapping(path = [Rels.EDIT_BOARD])
    fun editBoard() = editBoardRsp

    @GetMapping(path = [Rels.DELETE_BOARD])
    fun deleteBoard() = deleteBoardRsp
    

    
    @GetMapping(path = [Rels.GET_MULTIPLE_BLACKBOARDS])
    fun getBlackboards() = listBlackboardsRsp

    @GetMapping(path = [Rels.CREATE_BLACKBOARD])
    fun createBlackboard() = createBlackboardRsp

    @GetMapping(path = [Rels.GET_SINGLE_BLACKBOARD])
    fun getBlackboard() = getBlackboardRsp

    @GetMapping(path = [Rels.EDIT_BLACKBOARD])
    fun editBlackboard() = editBlackboardRsp

    @GetMapping(path = [Rels.DELETE_BLACKBOARD])
    fun deleteBlackboard() = deleteBlackboardRsp



    @GetMapping(path = [Rels.GET_MULTIPLE_BLACKBOARDITEMS])
    fun getBlackboardItems() = listBlackboardItemsRsp

    @GetMapping(path = [Rels.CREATE_BLACKBOARDITEM])
    fun createBlackboardItem() = createBlackboardItemRsp

    @GetMapping(path = [Rels.GET_SINGLE_BLACKBOARDITEM])
    fun getBlackboardItem() = getBlackboardItemRsp

    @GetMapping(path = [Rels.EDIT_BLACKBOARDITEM])
    fun editBlackboardItem() = editBlackboardItemRsp

    @GetMapping(path = [Rels.DELETE_BLACKBOARDITEM])
    fun deleteBlackboardItem() = deleteBlackboardItemRsp



    @GetMapping(path = [Rels.GET_MULTIPLE_USERS])
    fun getUsers() = listUsersRsp

    @GetMapping(path = [Rels.CREATE_USER])
    fun createUser() = createUserRsp

    @GetMapping(path = [Rels.GET_SINGLE_USER])
    fun getUser() = getUserRsp

    @GetMapping(path = [Rels.EDIT_USER])
    fun editUser() = editUserRsp

    @GetMapping(path = [Rels.DELETE_USER])
    fun deleteUser() = deleteUserRsp



    @GetMapping(path = [Rels.GET_MULTIPLE_TEMPLATES])
    fun getTemplates() = listTemplatesRsp

    @GetMapping(path = [Rels.CREATE_TEMPLATE])
    fun createTemplate() = createTemplateRsp

    @GetMapping(path = [Rels.GET_SINGLE_TEMPLATE])
    fun getTemplate() = getTemplateRsp

    @GetMapping(path = [Rels.EDIT_TEMPLATE])
    fun editTemplate() = editTemplateRsp

    @GetMapping(path = [Rels.DELETE_TEMPLATE])
    fun deleteTemplate() = deleteTemplateRsp



    @GetMapping(path = [Rels.CREATE_FORUM])
    fun createForum() = createForumRsp

    @GetMapping(path = [Rels.GET_SINGLE_FORUM])
    fun getForum() = getForumRsp

    @GetMapping(path = [Rels.EDIT_FORUM])
    fun editForum() = editForumRsp

    @GetMapping(path = [Rels.DELETE_FORUM])
    fun deleteForum() = deleteForumRsp



    @GetMapping(path = [Rels.GET_MULTIPLE_FORUMITEMS])
    fun getForumItems() = listForumItemsRsp

    @GetMapping(path = [Rels.CREATE_FORUMITEM])
    fun createForumItem() = createForumItemRsp

    @GetMapping(path = [Rels.GET_SINGLE_FORUMITEM])
    fun getForumItem() = getForumItemRsp

    @GetMapping(path = [Rels.EDIT_FORUMITEM])
    fun editForumItem() = editForumItemRsp

    @GetMapping(path = [Rels.DELETE_FORUMITEM])
    fun deleteForumItem() = deleteForumItemRsp



    @GetMapping(path = [Rels.GET_MULTIPLE_COMMENTS])
    fun getComments() = listCommentsRsp

    @GetMapping(path = [Rels.CREATE_COMMENT])
    fun createComment() = createCommentRsp

    @GetMapping(path = [Rels.GET_SINGLE_COMMENT])
    fun getComment() = getCommentRsp

    @GetMapping(path = [Rels.EDIT_COMMENT])
    fun editComment() = editCommentRsp

    @GetMapping(path = [Rels.DELETE_COMMENT])
    fun deleteComment() = deleteCommentRsp



    @GetMapping(path = [Rels.SUBSCRIBE])
    fun subscribe() = subscribeRsp

    @GetMapping(path = [Rels.UNSUBSCRIBE])
    fun unsubscribe() = unsubscribeRsp

    @GetMapping(path = [Rels.GET_BOARD_MEMBERS])
    fun getBoardMembers() = getBoardMembers

    @GetMapping(path = [Rels.GET_USER_BLACKBOARDS_SETTINGS])
    fun getUserBlackboardsSettings() = getUserBlackboardsSettings

    @GetMapping(path = [Rels.MY_BOARDS])
    fun getMyBoards() = getMyBoards

    @GetMapping(path = [Rels.HOME])
    fun getHome() = getHome

    @GetMapping(path = [Rels.NAVIGATION])
    fun getNavigation() = getNavigation

    @GetMapping(path = [Rels.GET_MULTIPLE_REPORTS])
    fun getReports() = listReportsRsp

    @GetMapping(path = [Rels.CREATE_REPORT])
    fun createReport() = createReportRsp

    @GetMapping(path = [Rels.GET_SINGLE_REPORT])
    fun getReport() = getReportRsp
}