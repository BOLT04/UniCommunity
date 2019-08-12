package isel.pt.unicommunity.model.webdto.rel_links

import com.fasterxml.jackson.annotation.JsonProperty
import isel.pt.unicommunity.model.webdto.BodyNavLink
import isel.pt.unicommunity.model.webdto.clean.CollectionJson
import isel.pt.unicommunity.model.webdto.Rels
import isel.pt.unicommunity.model.webdto.clean.NavLink




class CreateForumItemLink (href: String): BodyNavLink<ForumItemOutputDto, ForumItemInputDto>(Rels.CREATE_FORUMITEM, href, ForumItemOutputDto::class.java, ForumItemInputDto::class.java)
class GetSingleForumItemLink (href: String): NavLink<ForumItemInputDto>(Rels.GET_SINGLE_FORUMITEM, href, ForumItemInputDto::class.java)
class GetMultipleForumItemsLink (href: String): NavLink<CollectionJson>(Rels.GET_MULTIPLE_FORUMITEMS, href, CollectionJson::class.java)
class EditForumItemLink (href: String): BodyNavLink<ForumItemOutputDto, ForumItemInputDto>(Rels.EDIT_FORUMITEM, href, ForumItemOutputDto::class.java, ForumItemInputDto::class.java)
//todo class DeleteForumItemLink (href: String): NavLink(Rels.DELETE_FORUMITEM, href)


class ForumItemOutputDto


class ForumItemInputDto(
    val name : String,
    val content : String,
    val createdAt : String,
    val _links : ForumItemInputDtoLinkStruct,
    val _embedded : ForumItemInputDtoEmbeddedStruct?
)

class ForumItemInputDtoEmbeddedStruct {

}

class ForumItemInputDtoLinkStruct (

    val self : GetSingleForumItemLink?,
    @JsonProperty(Rels.NAVIGATION) val nav : NavigationLink?,
    @JsonProperty(Rels.GET_SINGLE_BOARD) val getSingleBoardLink: GetSingleBoardLink?,
    @JsonProperty(Rels.GET_SINGLE_FORUM) val getSingleForumLink: GetSingleForumLink?,
    @JsonProperty(Rels.CREATE_FORUMITEM) val createForumItemLink: CreateForumItemLink?,
    @JsonProperty(Rels.GET_MULTIPLE_FORUMITEMS) val getMultipleForumItemsLink: GetMultipleForumItemsLink?,
    @JsonProperty(Rels.EDIT_FORUMITEM) val editForumItemLink: EditForumItemLink?,
    @JsonProperty(Rels.DELETE_FORUMITEM) val deleteForumItemLink: CreateForumItemLink?,
    @JsonProperty(Rels.GET_MULTIPLE_COMMENTS) val getMultipleCommentsLink: GetMultipleCommentsLink?,
    @JsonProperty(Rels.CREATE_COMMENT) val createCommentLink: CreateCommentLink?
)


fun ForumItemFromCollectionJson(col : CollectionJson) : ForumItemCollection{

    with(col.collection){
        return ForumItemCollection(
            GetMultipleForumItemsLink(href),
            items.map {
                with(DataExtractor(it.data, it.links)) {
                    ForumItemCollection.PartialForumItem(
                        getValue("name"),
                        getOptionalValue("content"),
                        getValue("createdAt"),
                        getOptionalValue("authorName"),
                        GetSingleForumItemLink(it.href),
                        getLink(Rels.GET_MULTIPLE_COMMENTS, ::GetMultipleCommentsLink)
                    )
                }
            }
        )
    }
}

class ForumItemCollection(
    val self : GetMultipleForumItemsLink,
    val forumItems: List<PartialForumItem>
) {
    class PartialForumItem(

        val name: String,
        val content: String?,
        val createdAt: String,
        val authorName: String?, //todo

        val self: GetSingleForumItemLink,
        val getMultipleCommentsLink: GetMultipleCommentsLink?

    )
}