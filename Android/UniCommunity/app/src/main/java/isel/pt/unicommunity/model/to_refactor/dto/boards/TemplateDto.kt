package isel.pt.unicommunity.model.to_refactor.dto.boards

class TemplateDto(
    val name : String = "no value",
    val id : Int = -1,
    val hasForum : Boolean = false,

    val blackboardNames : Array<String>
)