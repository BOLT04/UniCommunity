package pt.isel.g20.unicommunity.auth.model

import com.fasterxml.jackson.annotation.JsonCreator

data class LoginDto @JsonCreator constructor(
        val email: String,
        val password: String
)