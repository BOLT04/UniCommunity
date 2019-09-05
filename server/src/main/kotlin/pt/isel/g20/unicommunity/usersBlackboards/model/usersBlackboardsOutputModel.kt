package pt.isel.g20.unicommunity.usersBlackboards.model

import pt.isel.g20.unicommunity.blackboard.model.PartialBlackboardObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject

class SingleUsersBlackboardResponse(
        usersBlackboard: UsersBlackboards
) : HalObject(mutableMapOf(), mutableMapOf()){
    val userId = usersBlackboard.user.id
    val bbId = usersBlackboard.blackboard.id
    val notificationLevel = usersBlackboard.notificationLevel
    val fcmTopicName = usersBlackboard.blackboard.getFcmTopicName()

    init {
        val blackboard = usersBlackboard.blackboard
        val user = usersBlackboard.user
        val partialUser = PartialUserObject(
                user.name,
                user.email,
                mapOf("self" to Link(Uri.forSingleUserText(user.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_USER to partialUser
        ))

        val partialBlackboard = PartialBlackboardObject(
                blackboard.name,
                mapOf("self" to Link(Uri.forSingleBlackboardText(blackboard.board.id, blackboard.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_BLACKBOARD to partialBlackboard
        ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleUsersBlackboardText(userId, bbId)),
                Rels.EDIT_USERS_BLACKBOARD to Link(Uri.forSingleUsersBlackboardText(userId, bbId)),
                Rels.GET_SINGLE_USER to Link(Uri.forSingleUserText(user.id)),
                Rels.GET_SINGLE_BLACKBOARD to Link(Uri.forSingleBlackboardText(blackboard.board.id, blackboard.id))
        ))
    }
}


class MultipleUsersBlackboardsResponse(
        userId: Long,
        usersBlackboards : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllUsersBlackboards(userId),
        links = mutableListOf(
                CollectionLink("self", Uri.forAllUsersBlackboards(userId))
        ),
        items = usersBlackboards
)

class PartialUsersBlackboardsObject(
        val userId: Long,
        val boardId: Long,
        val blackboardId: Long,
        val userEmail: String,
        val boardName: String,
        val blackboardName: String,
        val notificationLevel: String,
        val fcmTopicName: String,
        val _links: Map<String, Link>
) : IHalObj