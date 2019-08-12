package isel.pt.unicommunity.model.webdto.rel_links

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.webdto.BodyNavLink
import isel.pt.unicommunity.model.webdto.clean.CollectionJson
import isel.pt.unicommunity.model.webdto.Rels
import isel.pt.unicommunity.model.webdto.clean.NavLink


class GetMultipleBlackBoardItemsLink (
    href: String
): NavLink<CollectionJson>(
        Rels.GET_MULTIPLE_BLACKBOARDITEMS,
        href,
        CollectionJson::class.java
    )

class GetSingleBlackBoardItemLink ( href: String )
    : NavLink<BlackBoardItemInputDto>(
        Rels.GET_SINGLE_BLACKBOARDITEM,
        href,
        BlackBoardItemInputDto::class.java
    )

class CreateBlackBoardItemLink (
    href: String
) : BodyNavLink<BlackBoardItemOutputDto, BlackBoardItemInputDto>(
        Rels.CREATE_BLACKBOARDITEM,
        href,
        BlackBoardItemOutputDto::class.java,
        BlackBoardItemInputDto::class.java
    )

class EditBlackBoardItemLink (
    href: String
): BodyNavLink<BlackBoardItemOutputDto, BlackBoardItemInputDto>(
        Rels.EDIT_BLACKBOARDITEM,
        href,
        BlackBoardItemOutputDto::class.java,
        BlackBoardItemInputDto::class.java
    )

class DeleteBlackBoardItemLink (href: String)//todo : NavLink(Rels.DELETE_BLACKBOARDITEM, href)



class BlackBoardItemOutputDto(
    val name: String,
    val content: String?
)


class BlackBoardItemInputDto(
    val name: String,
    val content : String?,
    val createdAt : String,

    val _links : BlackBoardItemInputDtoLinkStruct
)

class BlackBoardItemInputDtoLinkStruct(
    val self :  GetSingleBlackBoardItemLink?,
    @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
    @JsonProperty(Rels.GET_SINGLE_BLACKBOARD) val getSingleBlackBoardLink: GetSingleBlackBoardLink?,
    @JsonProperty(Rels.GET_SINGLE_BOARD) val getSingleBoardLink: GetSingleBoardLink?,
    @JsonProperty(Rels.CREATE_BLACKBOARDITEM) val createBlackBoardItemLink: CreateBlackBoardItemLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDITEMS) val getMultipleBlackBoardItemsLink: GetMultipleBlackBoardItemsLink?,
    @JsonProperty(Rels.EDIT_BLACKBOARDITEM) val editBlackBoardItemLink: EditBlackBoardItemLink?//,
    //todo @JsonProperty(Rels.DELETE_BLACKBOARDITEM) val deleteBlackBoardItemLink: DeleteBlackBoardItemLink?
)

fun BlackBoardItemFromCollectionJson(col : CollectionJson) : BlackBoardItemCollection{
    with(col.collection){
        return BlackBoardItemCollection(
            GetMultipleBlackBoardItemsLink(href),
            items.map {
                with(DataExtractor(it.data, it.links)) {
                    PartialBlackBoardItem(
                        getValue("name"),
                        getValue("id"),
                        getOptionalValue("content"),
                        getValue("authorName"),
                        getValue("createdAt"),
                        GetSingleBlackBoardItemLink(it.href)
                    )
                }
            }
        )
    }
}

class BlackBoardItemCollection(
    val self: GetMultipleBlackBoardItemsLink,
    val blackboardItems: List<PartialBlackBoardItem>
)

class PartialBlackBoardItem (


    val name: String,
    val id: String,
    val content: String?,
    val authorName: String,
    val createdAt: String,

    val self: GetSingleBlackBoardItemLink //criado a partir do href

    // todo val editBlackBoardLink: EditBlackBoardLink?
)
