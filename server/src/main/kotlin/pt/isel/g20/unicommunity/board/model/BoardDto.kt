package pt.isel.g20.unicommunity.board.model

import com.fasterxml.jackson.annotation.JsonCreator

data class BoardDto @JsonCreator constructor(val id: String, val name: String, val description: String) {
    fun toModel(): Board = Board(id, name, description)
}