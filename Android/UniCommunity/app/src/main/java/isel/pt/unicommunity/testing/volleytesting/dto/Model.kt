package isel.pt.unicommunity.testing.volleytesting.dto

class Model(
    val name : String,
    val composite : ParsedComposite,
    val navigator : SimpleNavigator?
)

class SimpleNavigator(val url :String)

class ParsedComposite(
    val conc : String,
    val otherconc : String
)