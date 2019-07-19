package isel.pt.unicommunity.model.webdto

class CollectionJson(val collection: HalCollection)

class HalCollection(

    val version : String,
    val href: String,
    val links : Array<Link>,
    val items : Array<Item>
)

class Item(
    val href: String,
    val data: Array<Property>,
    val links: Array<Link>
)

class Property(
    val name:String,
    val value: String,
    val prompt : String? = null
)


class Link(
    val rel : String,
    val href: String,
    val prompt: String? = null
)

abstract class NavLink(
    val rel :String,
    val href: String
)