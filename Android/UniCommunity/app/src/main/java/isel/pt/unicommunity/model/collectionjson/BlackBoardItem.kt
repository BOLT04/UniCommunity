package isel.pt.unicommunity.model.collectionjson

import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.links.GetMultipleBlackBoardItemsLink
import isel.pt.unicommunity.model.links.GetSingleBlackBoardItemLink


fun CollectionJson.toBlackBoardItemCollection() : BlackBoardItemCollection {
    with(this.collection){
        return BlackBoardItemCollection(
            GetMultipleBlackBoardItemsLink(href),
            items.map {
                with(DataExtractor(it.data, it.links)) {
                    BlackBoardItemCollection.PartialBlackBoardItem(
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
){

    class PartialBlackBoardItem (

        val name: String,
        val id: String,
        val content: String?,
        val authorName: String,
        val createdAt: String,

        val self: GetSingleBlackBoardItemLink //criado a partir do href

        // todo val editBlackBoardLink: EditBlackBoardLink?
    )
}