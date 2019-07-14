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

    @GetMapping(path = [Rels.EDIT_BLACKBOARD])
    fun editBlackboard() = editBlackboardRsp
}