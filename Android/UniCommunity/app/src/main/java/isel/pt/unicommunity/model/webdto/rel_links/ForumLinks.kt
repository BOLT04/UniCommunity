package isel.pt.unicommunity.model.webdto.rel_links

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.webdto.BodyNavLink
import isel.pt.unicommunity.model.webdto.Rels
import isel.pt.unicommunity.model.webdto.clean.NavLink


class CreateForumLink (href: String): BodyNavLink<ForumOutputDto, ForumInputDto>(Rels.CREATE_FORUM, href, ForumOutputDto::class.java, ForumInputDto::class.java)
class GetSingleForumLink (href: String): NavLink<ForumInputDto>(Rels.GET_SINGLE_FORUM, href, ForumInputDto::class.java)
class EditForumLink (href: String): BodyNavLink<ForumOutputDto, ForumInputDto>(Rels.EDIT_FORUM, href, ForumOutputDto::class.java, ForumInputDto::class.java)
//todo class DeleteForumLink (href: String): NavLink(Rels.DELETE_FORUM, href)



class ForumOutputDto

class ForumInputDto(
    val id : String,
    val _links : ForumInputDtoLinkStruct,
    val _embedded : ForumInputDtoEmbeddedStruct
)

class ForumInputDtoLinkStruct(
    val self : GetSingleForumLink?,
    @JsonProperty(Rels.GET_MULTIPLE_FORUMITEMS) val getMultipleForumItemsLink: GetMultipleForumItemsLink?,
    @JsonProperty(Rels.CREATE_FORUMITEM) val createForumItemLink: CreateForumItemLink?
)

class ForumInputDtoEmbeddedStruct(
    @JsonProperty(Rels.GET_SINGLE_BOARD) val embeddedBoard : PartialBoardInputDto?,
    @JsonProperty(Rels.GET_MULTIPLE_FORUMITEMS) val embeddedForumItems : Array<PartialForumItemDto>?
){
    class PartialBoardInputDto(
        val name : String?,
        val _links: PartialBoardLinkStruct?
    ) {
        class PartialBoardLinkStruct(val self: GetSingleBoardLink?)
    }

    class PartialForumItemDto(
        val name: String?,
        val author: String?,
        val _links: PartialForumItemLinkStruct?
    ) {
        class PartialForumItemLinkStruct(val self: GetSingleForumItemLink?)
    }
}

