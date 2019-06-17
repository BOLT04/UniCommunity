package isel.pt.unicommunity.model.to_refactor.built

import isel.pt.unicommunity.model.to_refactor.linker.Retriever

class User(

    val name : String = "no value",
    val email : String = "no value",
    val githubId : String = "no value",


    val self : Retriever<User>? = null,
    val nav : Retriever<Nav>? = null,
    val edit : Retriever<String>? = null //todo
)