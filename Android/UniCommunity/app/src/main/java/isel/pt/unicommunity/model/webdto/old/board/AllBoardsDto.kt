package isel.pt.unicommunity.model.webdto.old.board

import isel.pt.unicommunity.model.webdto.*
import isel.pt.unicommunity.model.webdto.clean.CollectionContainer
import isel.pt.unicommunity.model.webdto.clean.NavLink
import isel.pt.unicommunity.model.webdto.clean.Property

class AllBoardsDto(
    val collection: CollectionContainer
)

class AllBoardsModel(
    val boards : List<PartialBoard>,
    val links : AllBoardsLinkStruct
)

class AllBoardsLinkStruct( //TODO
    /*val selfLink: NavLink?,
    val navigationLink: NavLink?*/
)

class PartialBoard(
    val name: String,
    val description: String?,
    val links: PartialBoardLinkStruct?
)

class PartialBoardLinkStruct(
    /*val selfLink: NavLink?,
    val editBoardBoard: NavLink?,
    val deleteBoardLink: NavLink?,
    val getMultipleBoardsLink: NavLink?*/
)
/*
class AllBoardsMapper{

    fun toModel(dto: AllBoardsDto): AllBoardsModel{

        val links = dto.collection.links.map { NavLink(it.rel, it.href) }

        val allBoardsLinkStruct = AllBoardsLinkStruct(
            links.firstOrNull { it.rel == "self" },
            links.firstOrNull { it.rel == Rels.NAVIGATION }
        )

        val items = dto.collection.items.map { item ->

            val itemLinks = item.links.map { NavLink(it.rel, it.href) }

            val partialBoardLinkStruct = PartialBoardLinkStruct(
                itemLinks.firstOrNull { it.rel == "self" },
                itemLinks.firstOrNull { it.rel == Rels.EDIT_BOARD },
                itemLinks.firstOrNull { it.rel == Rels.DELETE_BOARD },
                itemLinks.firstOrNull { it.rel == Rels.GET_MULTIPLE_BOARDS }
            )

            val data = item.data

            PartialBoard(
                getProperty(data, "APP_NAME"),
                getOptionalProperty(data, "description"),
                partialBoardLinkStruct
            )
        }
        return  AllBoardsModel(items, allBoardsLinkStruct)
    }
}*/

fun getOptionalProperty(data: Array<Property>, name: String): String? {
    return data.firstOrNull {it.name == name}?.value
}

fun getProperty(data: Array<Property>, name: String): String {
    return data.first{it.name == name}.value
}