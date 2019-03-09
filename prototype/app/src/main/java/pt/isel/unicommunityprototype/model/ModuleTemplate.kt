package pt.isel.unicommunityprototype.model

class ModuleTemplate {
    companion object {
        /**
         * Creates a list with all modules.
         */
        fun createTeacherTemplate(): MutableList<Module> =
            mutableListOf(
                Sumarios(), Recursos(), Anuncios(), Bibliografia()
            )

        fun createComTemplate(): MutableList<Module> =
            mutableListOf(Anuncios())
    }
}