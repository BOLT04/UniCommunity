package isel.pt.unicommunity.model

class CollectionJson(val collection: CollectionContainer)

class CollectionContainer(
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



