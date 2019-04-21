package pt.isel.g20.unicommunity.forum.model

import com.fasterxml.jackson.annotation.JsonCreator

data class ForumDto @JsonCreator constructor(
        val allowImagePosts: Boolean?
)