package isel.pt.unicommunity.model.outputdto

class BoardOutputDto(
    val name: String,
    val templateId: Long?,
    val description: String?,
    val blackboardNames: List<String>?,
    val hasForum: Boolean?
)