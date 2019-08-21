package pt.isel.g20.unicommunity.user.model

import pt.isel.g20.unicommunity.blackboardItem.model.PartialBlackboardItemObject
import pt.isel.g20.unicommunity.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.comment.model.PartialCommentObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.forumItem.model.PartialForumItemObject
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.usersBlackboards.PartialUsersBlackboardsObject

class SingleUserResponse(user: User) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = user.id
    val name = user.name
    val email = user.email
    val githubId = user.githubId

    init {
        if(user.boards.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_BOARDS to MultipleHalObj(user.boards.map {
                        PartialBoardObject(
                                it.name,
                                mapOf("self" to Link(Uri.forSingleBoardText(it.id)))
                        )
                    })
            ))

        if(user.bbItems.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_BLACKBOARDITEMS to MultipleHalObj(user.bbItems.map {
                        PartialBlackboardItemObject(
                                it.name,
                                it.author.name,
                                it.createdAt.toString(),
                                mapOf("self" to Link(Uri.forSingleBlackboardItemText(
                                        it.blackboard.board.id,
                                        it.blackboard.id,
                                        it.id
                                )))
                        )
                    })
            ))

        if(user.forumItems.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_FORUMITEMS to MultipleHalObj(user.forumItems.map {
                        PartialForumItemObject(
                                it.name,
                                it.author.name,
                                it.createdAt.toString(),
                                mapOf("self" to Link(Uri.forSingleForumItemText(
                                        it.forum.board.id,
                                        it.id
                                )))
                        )
                    })
            ))

        if(user.comments.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_COMMENTS to MultipleHalObj(user.comments.map {
                        PartialCommentObject(
                                it.content,
                                it.author.name,
                                it.createdAt.toString(),
                                mapOf("self" to Link(Uri.forSingleCommentText(
                                        it.forumItem.forum.board.id,
                                        it.forumItem.id,
                                        it.id
                                )))
                        )
                    })
            ))

        if(user.blackboardsSettings.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_USER_BLACKBOARDS_SETTINGS to MultipleHalObj(user.blackboardsSettings.map {
                        PartialUsersBlackboardsObject(
                                it.user.id,
                                it.blackboard.board.id,
                                it.blackboard.id,
                                it.user.email,
                                it.blackboard.board.name,
                                it.blackboard.name,
                                it.notificationLevel,
                                mapOf()
                        )
                    })
            ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleUserText(id)),
                Rels.EDIT_USER to Link(Uri.forSingleUserText(id))
        ))
    }
}


class MultipleUsersResponse(
        users : Iterable<User>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllUsers(),
        links = mutableListOf(
                CollectionLink("self",Uri.forAllUsers())
        ),
        items = users.map { Item( Uri.forSingleUserText(it.id)) }
)

class PartialUserObject(
        val name: String,
        val _links: Map<String, Link>
) : IHalObj