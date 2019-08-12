package isel.pt.unicommunity.model.webdto.rel_links

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.webdto.*
import isel.pt.unicommunity.model.webdto.clean.CollectionJson
import isel.pt.unicommunity.model.webdto.clean.Link
import isel.pt.unicommunity.model.webdto.clean.NavLink
import isel.pt.unicommunity.model.webdto.clean.Property


class GetMultipleBoardsLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_BOARDS, href, CollectionJson::class.java)
class GetSingleBoardLink (href: String): NavLink<BoardInputDto>(Rels.GET_SINGLE_BOARD, href, BoardInputDto::class.java)
class CreateBoardLink (href: String): BodyNavLink<BoardOutputDto, BoardInputDto>(Rels.CREATE_BOARD, href, BoardOutputDto::class.java, BoardInputDto::class.java)
class EditBoardLink (href: String): BodyNavLink<BoardOutputDto, BoardInputDto>(Rels.EDIT_BOARD, href, BoardOutputDto::class.java, BoardInputDto::class.java)
class DeleteBoardLink (href: String)//todo: NavLink(Rels.DELETE_BOARD, href)


class BoardInputDto(
    val name : String,
    val description: String?,
    val _links : BoardInputDtoLinkStruct,
    val _embedded : BoardInputDtoEmbeddedStruct?
)

class BoardInputDtoEmbeddedStruct(
    @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDS) val smallBlackBoardBoards : Array<Board_PartialBlackBoard>?
)
class Board_PartialBlackBoard(
   val name: String?,
   val _links: PartialBlackBoardLinkStruct
)
class PartialBlackBoardLinkStruct(
    val self: GetSingleBlackBoardLink?
)

class BoardInputDtoLinkStruct(
    val self : GetSingleBoardLink?,
    @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDS) val getMultipleBlackBoardsLink: GetMultipleBoardsLink?,
    @JsonProperty(Rels.CREATE_BOARD) val createBoardLink: CreateBoardLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BOARDS) val getMultipleBoardsLink: GetMultipleBoardsLink?,
    @JsonProperty(Rels.EDIT_BOARD) val editBoardLink: EditBoardLink?,
    //@JsonProperty(Rels.DELETE_BOARD) val deleteBoardLink: DeleteBoardLink?, TODO

    @JsonProperty(Rels.GET_MULTIPLE_FORUMITEMS) val getMultipleForumItemsLink: GetMultipleForumItemsLink?,
    @JsonProperty(Rels.GET_SINGLE_FORUM) val getSingleForumLink: GetSingleForumLink?
)



class BoardOutputDto(
    val name: String,
    val templateId: Long?,
    val description: String?,
    val blackboardNames: List<String>?,
    val hasForum: Boolean?
)


fun BoardFromCollectionJson(col : CollectionJson) : BoardCollection{

    with(col.collection){
        return BoardCollection(
            href,
            items.map {
                with(DataExtractor(it.data, it.links)) {
                    PartialBoardItem(
                        it.href,
                        getValue("name"),
                        getValue("id"),
                        getOptionalValue("description"),
                        getLink(Rels.EDIT_BOARD, ::EditBoardLink),
                        getLink(Rels.DELETE_BOARD, ::DeleteBoardLink),
                        getLink(Rels.GET_MULTIPLE_BOARDS, ::GetMultipleBoardsLink)
                    )
                }
            }
        )
    }

}

class DataExtractor(data : Array<Property>, links: Array<Link>){
    private val propMap = HashMap<String,String>()
    private val linkMap = HashMap<String,String>()
    init {
        data.forEach { propMap[it.name]= it.value }
        links.forEach { linkMap[it.rel]=it.href }
    }

    fun getValue(propName: String): String {

        return propMap[propName] ?: throw NotValidPropertyException(propName)

    }

    fun getOptionalValue(propName: String): String? {

        return propMap[propName]

    }

    fun <T>getLink(rel: String, constructor: (String) -> T ): T? {
        val href = linkMap[rel] ?: return null
        return constructor(href)
    }


}

class NotValidPropertyException(propName: String) : Throwable()



class BoardCollection(
    val href: String,
    val boards : List<PartialBoardItem>
)


class PartialBoardItem(
    val href: String,

    val name:String,
    val id : String,
    val description:String?,

    val editBoardLink: EditBoardLink?,
    val deleteBoardLink: DeleteBoardLink?,
    val getMultipleBoardsLink: GetMultipleBoardsLink?
    // add member
    // remove member
)