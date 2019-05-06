package isel.pt.unicommunity.testing.volleytesting.dto

class Dto(
    val name : String = "",
    val thing : String? = null,
    val composite : Composite,
    val navigator : String?
)

class Composite(
    val composite_name : String = "",
    val composite_value: String = ""
)

