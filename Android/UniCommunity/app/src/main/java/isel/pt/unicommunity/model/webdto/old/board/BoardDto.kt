package isel.pt.unicommunity.model.webdto.old.board

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.webdto.*
import isel.pt.unicommunity.model.webdto.old.blackboard.SmallBlackBoardDto
import isel.pt.unicommunity.model.webdto.clean.NavLink

class BoardInputDto(
    val name : String,
    val description: String?,
    val templateName:String,
    val hasForum: Boolean,
    val _links : BoardInputDtoLinkStruct,
    val _embedded : BoardEmbeddedStruct?
)

class BoardEmbeddedStruct(
    @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDS) val smallBlackBoards : Array<SmallBlackBoardDto>?
)


class BoardInputDtoLinkStruct(
    /*val self : SelfLink?,
    @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BLACKBOARDS) val getMultipleBlackBoardsLink: GetMultipleBoardsLink?,
    @JsonProperty(Rels.CREATE_BOARD) val createBoardLink: CreateBoardLink?,
    @JsonProperty(Rels.GET_MULTIPLE_BOARDS) val getMultipleBoardsLink: GetMultipleBoardsLink?,
    @JsonProperty(Rels.EDIT_BOARD) val editBoardLink: EditBoardLink?,
    @JsonProperty(Rels.DELETE_BOARD) val deleteBoardLink: DeleteBoardLink?,
    @JsonProperty(Rels.GET_MULTIPLE_FORUMITEMS) val getMultipleForumItemsLink: GetMultipleForumItemsLink?,
    @JsonProperty(Rels.GET_SINGLE_FORUM) val getSingleForumLink: GetSingleForumLink?*/
)


/*class Board(
    val APP_NAME : String,
    val description: String?,
    val templateName:String,
    val hasForum: Boolean,
    val _links : BoardLinkStruct,
    val _embedded : BoardEmbeddedStruct?
)

class BoardLinkStruct(
    val self : NavLink?,
    val nav : NavLink?,
    val getMultipleBlackBoardsLink: NavLink?,
    val createBoardLink: NavLink?,
    val getMultipleBoardsLink: NavLink?,
    val editBoardLink: NavLink?,
    val deleteBoardLink: NavLink?,
    val getMultipleForumItemsLink: NavLink?,
    val getSingleForumLink: NavLink?
)

class BoardMapper {

    fun dtoToModel(dto: BoardInputDto) : Board{

        return Board(
            dto.APP_NAME,
            dto.description,
            dto.templateName,
            dto.hasForum,
            dto._links.let {
                BoardLinkStruct(
                    self= it.self,
                    nav= it.nav,
                    getMultipleBlackBoardsLink= it.getMultipleBlackBoardsLink,
                    createBoardLink= it.createBoardLink,
                    getMultipleBoardsLink= it.getMultipleBoardsLink,
                    editBoardLink= it.editBoardLink,
                    deleteBoardLink= it.deleteBoardLink,
                    getMultipleForumItemsLink= it.getMultipleForumItemsLink,
                    getSingleForumLink= it.getSingleForumLink
                )
            },
            dto._embedded)

    }

}
*/


