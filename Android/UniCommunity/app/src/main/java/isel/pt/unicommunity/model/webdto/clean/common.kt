package isel.pt.unicommunity.model.webdto.clean

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnore
import isel.pt.unicommunity.model.webdto.rel_links.DeleteBoardLink
import isel.pt.unicommunity.model.webdto.rel_links.EditBoardLink
import isel.pt.unicommunity.model.webdto.rel_links.GetMultipleBoardsLink
import java.io.Serializable

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

open class NavLink<T>(
    @JsonIgnore val rel :String,
    val href: String,
    @JsonIgnore val responseClass : Class<T>
)



