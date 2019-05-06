package isel.pt.unicommunity.model.refactor_me_dad.dto.boards

class TemplateMapper(
    val name : String = "no value",
    val id : Int = -1,
    val hasForum : Boolean = false,

    val blackboardNames : Array<String>
)