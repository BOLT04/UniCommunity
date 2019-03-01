package pt.isel.unicommunityprototype.repository

import pt.isel.unicommunityprototype.model.Board
import pt.isel.unicommunityprototype.model.Module

class Repository {

    val boards = mutableListOf<Board>()

    fun createBoard(name: String,
                    description: String?,
                    modules: List<Module>) {
        boards.add(Board(name, description, modules))
    }
}