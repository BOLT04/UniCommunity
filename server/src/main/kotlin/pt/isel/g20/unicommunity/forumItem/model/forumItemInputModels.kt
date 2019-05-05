package pt.isel.g20.unicommunity.forumItem.model

import com.fasterxml.jackson.annotation.JsonCreator

data class ForumItemDto @JsonCreator constructor(
        val name: String,
        val content: String,
        val anonymousPost: Boolean
)