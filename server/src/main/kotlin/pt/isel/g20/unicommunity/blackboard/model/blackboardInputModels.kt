package pt.isel.g20.unicommunity.blackboard.model

import com.fasterxml.jackson.annotation.JsonCreator

data class BlackboardDto @JsonCreator constructor(
        val name: String,
        val description: String,
        val notificationLevel: String
)