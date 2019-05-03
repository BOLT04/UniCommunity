package pt.isel.g20.unicommunity.comment.model

import com.fasterxml.jackson.annotation.JsonCreator

data class CommentDto @JsonCreator constructor(
        val content: String
)