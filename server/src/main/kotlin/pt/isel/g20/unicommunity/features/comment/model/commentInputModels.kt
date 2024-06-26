package pt.isel.g20.unicommunity.features.comment.model

import com.fasterxml.jackson.annotation.JsonCreator

data class CommentDto @JsonCreator constructor(
        val content: String,
        val anonymous: Boolean
)