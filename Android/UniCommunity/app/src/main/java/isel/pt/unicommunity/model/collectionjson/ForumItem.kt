package isel.pt.unicommunity.model.collectionjson

import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.links.GetMultipleCommentsLink
import isel.pt.unicommunity.model.links.GetMultipleForumItemsLink
import isel.pt.unicommunity.model.links.GetSingleForumItemLink

fun CollectionJson.toForumItemCollection() : ForumItemCollection {

    with(this.collection){
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
        val authorName: String?,

        val self: GetSingleForumItemLink,
        val getMultipleCommentsLink: GetMultipleCommentsLink?

    )
}