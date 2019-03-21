package pt.isel.g20.unicommunity.blackboard.model

import com.fasterxml.jackson.annotation.JsonCreator

data class BlackboardDto @JsonCreator constructor(
        val id: String,
        val name: String,
        val description: String,
        val notificationLevel: String
) {
    fun toModel(): Blackboard = Blackboard(id, name, description, notificationLevel)
}