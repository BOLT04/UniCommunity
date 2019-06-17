package isel.pt.unicommunity.model.to_refactor.dto


class HalStructure<DTO> (
    val url: String,
    val dto : Class<DTO>,
    val requiredHeaders: List<String>?,
    val body: Map<String, String>?
//todo method
)