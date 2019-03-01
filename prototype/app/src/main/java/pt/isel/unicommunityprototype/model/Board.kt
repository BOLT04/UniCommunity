package pt.isel.unicommunityprototype.model

data class Board(
    val name: String,
    val description: String?,
    val modules: List<Module>
)