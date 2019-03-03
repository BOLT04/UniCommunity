package pt.isel.unicommunityprototype.model

sealed class Module(val name: String)

class Anuncios: Module("Anuncios")
class Sumarios: Module("Sumarios")
class Recursos: Module("Recursos")
class Forum: Module("Forum")
class Bibliografia: Module("Bibliografia")
