package pt.isel.g20.unicommunity.user.model

import com.fasterxml.jackson.annotation.JsonCreator

data class UserDto @JsonCreator constructor(val id: String, val name: String, val email: String) {
    fun toModel(): User = User(id, name, email)
}