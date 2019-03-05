package pt.isel.unicommunityprototype.model

data class Board(
    val id: Int,
    val name: String,
    val description: String?,
    private val modules: List<Module>, //TODO: forum is a module, but we want to distinguish forum from blackboards for easy access to forum!! Find a better way of modeling these classes
    val forum: Forum?
) {
    fun getAnunciosBlackboard() : Anuncios? {
        for (module in modules) {
            if (module is Anuncios) return module
        }

        return null
    }
}