package isel.pt.unicommunity.model.webdto.old.blackboard

import isel.pt.unicommunity.model.webdto.Rels
import isel.pt.unicommunity.model.webdto.old.board.getOptionalProperty
import isel.pt.unicommunity.model.webdto.old.board.getProperty
import isel.pt.unicommunity.model.webdto.clean.CollectionContainer
import isel.pt.unicommunity.model.webdto.clean.NavLink


class AllBlackBoardsDto(
    val collection: CollectionContainer
)



class AllBlackBoardsModel(
    val blackBoards : List<PartialBlackBoard>,
    val links : AllBlackBoardsLinkStruct
)

class AllBlackBoardsLinkStruct( //TODO
    /*val selfLink: NavLink?,
    val navigationLink: NavLink?*/
)

class PartialBlackBoard(
    val name: String,
    val description: String?,
    val notificationLevel: String,
    val links: PartialBlackBoardLinkStruct?
)

class PartialBlackBoardLinkStruct(
    /*val selfLink: NavLink?,
    val editBlackBoardBoard: NavLink?,
    val deleteBlackBoardLink: NavLink?,
    val getMultipleBlackBoardsLink: NavLink?,
    val getMultipleBoardsLink: NavLink?*/
)

/*class AllBlackBoardsMapper{

    fun toModel(dto: AllBlackBoardsDto): AllBlackBoardsModel{

        val links = dto.collection.links.map { NavLink(it.rel, it.href) }

        val allBlackBoardsLinkStruct = AllBlackBoardsLinkStruct(
            links.firstOrNull { it.rel == "self" },
            links.firstOrNull { it.rel == Rels.NAVIGATION }
        )

        val items = dto.collection.items.map { item ->

            val itemLinks = item.links.map { NavLink(it.rel, it.href) }

            val partialBoardLinkStruct = PartialBlackBoardLinkStruct(
                itemLinks.firstOrNull { it.rel == "self" },
                itemLinks.firstOrNull { it.rel == Rels.EDIT_BLACKBOARD },
                itemLinks.firstOrNull { it.rel == Rels.DELETE_BLACKBOARD },
                itemLinks.firstOrNull { it.rel == Rels.GET_MULTIPLE_BLACKBOARDS },
                itemLinks.firstOrNull { it.rel == Rels.GET_MULTIPLE_BOARDS }
            )

            val data = item.data

            Board_PartialBlackBoard(
                getProperty(data, "APP_NAME"),
                getOptionalProperty(data, "description"),
                getProperty(data, "notificationLevel"),
                partialBoardLinkStruct
            )
        }
        return  AllBlackBoardsModel(items, allBlackBoardsLinkStruct)
    }
}*/