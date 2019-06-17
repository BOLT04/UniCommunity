package isel.pt.unicommunity.model.to_refactor.built

import isel.pt.unicommunity.model.to_refactor.linker.Retriever

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