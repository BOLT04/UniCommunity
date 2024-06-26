package pt.isel.g20.unicommunity.features.user.model

import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.features.board.model.PartialBoardObject
import pt.isel.g20.unicommunity.features.usersBlackboards.model.PartialUsersBlackboardsObject

class SingleUserResponse(sessionUser: User, user: User) : HalObject(mutableMapOf(), mutableMapOf()){
    val id = user.id
    val name = user.name
    val email = user.email
    val role = user.role
    val githubId = user.githubId

    init {
        val userIsAuthor = user.id == sessionUser.id
        if(user.usersBoards.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_BOARDS to MultipleHalObj(user.getBoards().map {
                        PartialBoardObject(
                                it.name,
                                mapOf("self" to Link(Uri.forSingleBoardText(it.id)))
                        )
                    })
            ))

        /*if(user.bbItems.size != 0)
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
                        if(userIsAuthor || !it.anonymousPost){
                             PartialForumItemObject(
                                     it.name,
                                     it.content,
                                     it.author.name,
                                     it.createdAt.toString(),
                                     mapOf("self" to Link(Uri.forSingleForumItemText(
                                            it.forum.board.id,
                                            it.id
                                     )))
                             )
                        }
                    })
            ))

        if(user.comments.size != 0)
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_MULTIPLE_COMMENTS to MultipleHalObj(user.comments.map {
                        if(userIsAuthor || !it.anonymousComment){
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
                        }
                    })
            ))*/

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
                                it.blackboard.getFcmTopicName(),
                                mapOf()
                        )
                    })
            ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleUserText(id))
        ))

        if(userIsAuthor)
            super._links?.putAll(sequenceOf(
                    Rels.EDIT_USER to Link(Uri.forSingleUserText(id))
            ))
    }
}


class MultipleUsersResponse(
        users : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllUsers(),
        links = mutableListOf(
                CollectionLink("self", Uri.forAllUsers())
        ),
        items = users
)

class PartialUserObject(
        val name: String,
        val email: String,
        val _links: Map<String, Link>
) : IHalObj