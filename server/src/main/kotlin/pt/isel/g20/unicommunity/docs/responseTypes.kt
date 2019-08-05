package pt.isel.g20.unicommunity.docs

import pt.isel.g20.unicommunity.common.APPLICATION_JSON
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.hateoas.*

class EditBlackboardHalFormsResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.EDIT_BLACKBOARD)
    ), HalFormsTemplateObject(
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

class ListBlackboardsResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.GET_MULTIPLE_BLACKBOARDS)
    ), HalFormsTemplateObject(
        HalFormsTemplate(
            title = "List blackboards of board",
            method = "get"
        )
    )
)

class CreateBlackboardResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.CREATE_BLACKBOARD)
    ), HalFormsTemplateObject(
        HalFormsTemplate(
            title = "Create a blackboard on a board",
            method = "post",
            contentType = APPLICATION_JSON,
            properties = emptyList()//TODO: this
        )
    )
)

class DeleteBlackboardResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.DELETE_BLACKBOARD)
    ), HalFormsTemplateObject(
        HalFormsTemplate(
            title = "Delete a blackboard from a board",
            method = "delete",
            contentType = APPLICATION_JSON,
            properties = listOf()
        )
    )
)

class EditBoardHalFormsResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.EDIT_BOARD)
    ), HalFormsTemplateObject(
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

class AddMemberToBoardHalFormsResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.ADD_MEMBER_TO_BOARD)
    ), HalFormsTemplateObject(
        HalFormsTemplate(
            title = "Add a user to a board",
            method = "post"
        )
    )
)

class RemoveMemberToBoardHalFormsResponse : HalFormsObject(
    mapOf(
        "self" to Link(Rels.REMOVE_MEMBER_TO_BOARD)
    ), HalFormsTemplateObject(
        HalFormsTemplate(
            title = "Remove a user of a board",
            method = "post"
        )
    )
)