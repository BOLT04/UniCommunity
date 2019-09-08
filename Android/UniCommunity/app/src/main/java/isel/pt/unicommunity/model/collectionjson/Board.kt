package isel.pt.unicommunity.model.collectionjson

import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.links.*

fun CollectionJson.toBoardCollection() : BoardCollection {

    with(this.collection){
        return BoardCollection(
            GetMultipleBoardsLink(href),
            items.map {
                with(DataExtractor(it.data, it.links)) {
                    BoardCollection.PartialBoard(
                        GetSingleBoardLink(it.href),
                        getValue("name"),
                        getValue("id"),
                        getOptionalValue("description"),
                        getLink(Rels.EDIT_BOARD, ::EditBoardLink),
                        getLink(Rels.GET_MULTIPLE_BOARDS, ::GetMultipleBoardsLink),
                        getLink(Rels.SUBSCRIBE, ::SubscribeLink),
                        getLink(Rels.UNSUBSCRIBE, ::UnsubscribeLink)
                    )
                }
            }
        )
    }

}


class BoardCollection(
    val self: GetMultipleBoardsLink,
    val boards : List<PartialBoard>
){
    class PartialBoard(
        val self: GetSingleBoardLink,

        val name:String,
        val id : String,
        val description:String?,

        val editBoardLink: EditBoardLink?,
        val getMultipleBoardsLink: GetMultipleBoardsLink?,
        val subscribeLink: SubscribeLink?,
        val unsubscribeLink: UnsubscribeLink?
    )
}