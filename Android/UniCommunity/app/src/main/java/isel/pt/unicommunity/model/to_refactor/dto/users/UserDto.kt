package isel.pt.unicommunity.model.to_refactor.dto.users

import isel.pt.unicommunity.model.to_refactor.dto.HalStructure
import isel.pt.unicommunity.model.to_refactor.dto.navigation.NavDto

class UserDto(
    val name : String = "no value",
    val email : String = "no value",
    val githubId : String = "no value",

    //val test : HalStructure<TestDto>

   // val self : HalStructure? = null,*/
    val nav : HalStructure<NavDto>? = null //ha
    //val edit : HalStructure? = null*/
)