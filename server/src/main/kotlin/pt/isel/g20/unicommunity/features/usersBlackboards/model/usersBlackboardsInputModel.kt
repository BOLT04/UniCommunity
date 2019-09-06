package pt.isel.g20.unicommunity.features.usersBlackboards.model

import com.fasterxml.jackson.annotation.JsonCreator

data class UsersBlackboardsDto @JsonCreator constructor(
        val notificationLevel: String
)