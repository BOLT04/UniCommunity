package pt.isel.g20.unicommunity.usersBlackboards.model

import com.fasterxml.jackson.annotation.JsonCreator

data class UsersBlackboardsDto @JsonCreator constructor(
        val notificationLevel: String
)