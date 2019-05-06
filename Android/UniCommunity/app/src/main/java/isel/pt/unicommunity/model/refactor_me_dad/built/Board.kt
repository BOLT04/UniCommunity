package isel.pt.unicommunity.model.refactor_me_dad.built

import isel.pt.unicommunity.model.refactor_me_dad.linker.Retriever

class Board(
    val parent: Board?, //todo meh
    val name: String,
    val description: String,
    val template : Template,
    val navigation: Retriever<Nav>? = null
) {

}

class Template(
    val modules : List<Module> //todo meh
)

class Nav