package pt.isel.g20.unicommunity.features.usersBlackboards.model

import pt.isel.g20.unicommunity.common.*

fun UsersBlackboards.toItemRepr() : Item {
    val userId = this.user.id
    val bbId = this.blackboard.id
    val boardId = this.blackboard.board.id
    val notificationLevel = this.notificationLevel
    val bbFcmTopicName = this.blackboard.getFcmTopicName()

    val links = mutableListOf(
            CollectionLink(
                    rel = "self",
                    href = Uri.forSingleUsersBlackboardText(userId, bbId)
            ),
            CollectionLink(
                    rel = Rels.GET_SINGLE_BLACKBOARD,
                    href = Uri.forSingleBlackboardText(boardId, bbId)
            ),
            CollectionLink(
                    rel = Rels.GET_SINGLE_USER,
                    href = Uri.forSingleBoardText(userId)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_USERS_BLACKBOARDS,
                    href = Uri.forAllUsersBlackboards(userId)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_BLACKBOARDITEMS,
                    href = Uri.forAllBlackboardItems(boardId, bbId)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_BLACKBOARDS,
                    href = Uri.forAllBlackboards(boardId)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_BOARDS,
                    href = Uri.forAllBoards()
            ),
            CollectionLink(
                    rel = Rels.EDIT_USERS_BLACKBOARD,
                    href = Uri.forSingleUsersBlackboardText(userId, bbId)
            )
    )

    return Item(
            href = Uri.forSingleUsersBlackboardText(userId, bbId),
            data = listOf(
                    Data(name = "userId", value = userId.toString()),
                    Data(name = "bbId", value = bbId.toString()),
                    Data(name = "notificationLevel", value = notificationLevel),
                    Data(name = "bbFcmTopicName", value = bbFcmTopicName)
            ),
            links = links
    )
}