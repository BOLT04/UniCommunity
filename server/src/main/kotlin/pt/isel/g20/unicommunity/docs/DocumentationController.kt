package pt.isel.g20.unicommunity.docs

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.g20.unicommunity.common.APPLICATION_HAL_FORMS_JSON
import pt.isel.g20.unicommunity.common.Rels

@RestController
@RequestMapping(produces = [APPLICATION_HAL_FORMS_JSON])
class DocumentationController {

    // Since the responses of this controller are all static and never change, only compute them once.
    val editBlackboardRsp = EditBlackboardHalFormsResponse()
    val listBlackboardsRsp = ListBlackboardsResponse()
    val editBoardRsp = EditBoardHalFormsResponse()
    val addMemberToBoardRsp = AddMemberToBoardHalFormsResponse()
    val removeMemberToBoardRsp = RemoveMemberToBoardHalFormsResponse()

    @GetMapping(path = [Rels.EDIT_BLACKBOARD])
    fun editBlackboard() = editBlackboardRsp

    @GetMapping(path = [Rels.GET_MULTIPLE_BLACKBOARDS])
    fun getBlackboards() = listBlackboardsRsp

    @GetMapping(path = [Rels.EDIT_BOARD])
    fun editBoard() = editBoardRsp

    @GetMapping(path = [Rels.ADD_MEMBER_TO_BOARD])
    fun addMemberToBoard() = addMemberToBoardRsp

    @GetMapping(path = [Rels.REMOVE_MEMBER_TO_BOARD])
    fun removeMemberToBoard() = removeMemberToBoardRsp
}