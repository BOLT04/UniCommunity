package isel.pt.unicommunity.model.webdto.rel_links

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.webdto.*
import isel.pt.unicommunity.model.webdto.clean.CollectionJson
import isel.pt.unicommunity.model.webdto.clean.NavLink

class GetMultipleBlackBoardsLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_BLACKBOARDS, href, CollectionJson::class.java)
class GetSingleBlackBoardLink (href: String): NavLink<BlackBoardInputDto>(Rels.GET_SINGLE_BLACKBOARD, href, BlackBoardInputDto::class.java)
class CreateBlackBoardLink (href: String): BodyNavLink<BlackBoardOutputDto, BlackBoardInputDto>(Rels.CREATE_BLACKBOARD, href, BlackBoardOutputDto::class.java, BlackBoardInputDto::class.java)
class EditBlackBoardLink (href: String): BodyNavLink<BlackBoardOutputDto, BlackBoardInputDto>(Rels.EDIT_BLACKBOARD, href, BlackBoardOutputDto::class.java, BlackBoardInputDto::class.java)
class DeleteBlackBoardLink (href: String)//todo : NavLink(Rels.DELETE_BLACKBOARD, href)



class BlackBoardInputDto(
    //val boardName: String,
    val name: String,
    val description: String?,
    val notificationLevel: String?,
    //val numberOfItems: String?//,
    //val items : Array<Something>,
    val _links : BlackBoardLinkStruct
)
class Something //this is the embedded
/* todo
    (val APP_NAME : String,
    val text : String)
*/

class BlackBoardLinkStruct(
    val self : GetSingleBlackBoardLink?,
    @JsonProperty(Rels.GET_SINGLE_BOARD) val getSingleBoardLink: GetSingleBoardLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDS) val getMultipleBlackBoardsLink: GetMultipleBlackBoardsLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDITEMS) val getMultipleBlackBoardItemsLink: GetMultipleBlackBoardItemsLink?,
    @JsonProperty(Rels.CREATE_BLACKBOARDITEM) val createBlackBoardItemLink: CreateBlackBoardItemLink?,
    @JsonProperty(Rels.EDIT_BLACKBOARD) val editBlackBoardLink: EditBlackBoardLink?
    //todo @JsonProperty(Rels.DELETE_BLACKBOARD) val deleteBlackBoardLink: DeleteBlackBoardLink?
)


fun blackBoardFromCollectionJson(col : CollectionJson) : BlackBoardCollection{

    with(col.collection){
        return BlackBoardCollection(
            href,
            items.map {
                with(DataExtractor(it.data, it.links)){
                    PartialBlackBoard(
                        it.href,
                        getValue("name"),
                        getValue("id"),
                        getOptionalValue("description"),
                        getOptionalValue("notificationLevel"),
                        getLink(Rels.EDIT_BLACKBOARD, ::EditBlackBoardLink),
                        getLink(Rels.DELETE_BLACKBOARD, ::DeleteBlackBoardLink),
                        getLink(Rels.GET_MULTIPLE_BLACKBOARDS, ::GetMultipleBlackBoardsLink),
                        getLink(Rels.GET_SINGLE_BOARD, ::GetSingleBoardLink)
                    )
                }
            }
        )
    }



}


class BlackBoardCollection(
    val href: String,
    val blackBoards : List<PartialBlackBoard>
)

class PartialBlackBoard(
    val href: String,

    val name: String,
    val id: String,
    val description: String?,
    val notificationLevel: String?,

    val editBlackBoardLink: EditBlackBoardLink?,
    val deleteBlackBoardLink: DeleteBlackBoardLink?,
    val getMultipleBlackBoardsLink: GetMultipleBlackBoardsLink?,
    val getSingleBoardLink: GetSingleBoardLink?
)


class BlackBoardOutputDto