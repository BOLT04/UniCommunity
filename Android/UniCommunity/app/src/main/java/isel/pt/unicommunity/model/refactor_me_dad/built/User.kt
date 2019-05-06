package isel.pt.unicommunity.model.refactor_me_dad.built

import isel.pt.unicommunity.model.refactor_me_dad.linker.Retriever

class User(

    val name : String = "no value",
    val email : String = "no value",
    val githubId : String = "no value",


    val self : Retriever<User>? = null,
    val nav : Retriever<Nav>? = null,
    val edit : Retriever<String>? = null //todo
)