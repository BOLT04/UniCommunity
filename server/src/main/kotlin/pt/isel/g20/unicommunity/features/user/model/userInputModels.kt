package pt.isel.g20.unicommunity.features.user.model

import com.fasterxml.jackson.annotation.JsonCreator

data class UserDto @JsonCreator constructor(
        val name: String,
        val email: String,
        val password: String,
        val role: String,
        val githubId: String?
)