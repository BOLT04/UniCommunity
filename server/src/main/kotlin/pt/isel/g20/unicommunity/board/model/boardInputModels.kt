package pt.isel.g20.unicommunity.board.model

import com.fasterxml.jackson.annotation.JsonCreator

data class CreateBoardDto @JsonCreator constructor(
    val name: String,
    val templateId: Long?,
    val description: String?,
    val blackboardNames: List<String>?,
    val hasForum: Boolean?,
    val fcmToken: String? = null
)

data class BoardDto @JsonCreator constructor(
        val name: String,
        val isActive: Boolean,
        val description: String?
)

data class SubscribeDto @JsonCreator constructor(val token: String)
