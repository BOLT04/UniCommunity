package isel.pt.unicommunity.model.collectionjson

import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.Rels
import isel.pt.unicommunity.model.links.*

fun CollectionJson.toBlackBoardCollection() : BlackBoardCollection {

    with(this.collection){
        return BlackBoardCollection(
            GetMultipleBlackBoardsLink(href),
            items.map {
                with(DataExtractor(it.data, it.links)) {
                    BlackBoardCollection.PartialBlackBoard(
                        GetSingleBlackBoardLink(it.href),
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
    val self: GetMultipleBlackBoardsLink,
    val blackBoards : List<PartialBlackBoard>
){
    class PartialBlackBoard(
        val self: GetSingleBlackBoardLink,

        val name: String,
        val id: String,
        val description: String?,
        val notificationLevel: String?,

        val editBlackBoardLink: EditBlackBoardLink?,
        val deleteBlackBoardLink: DeleteBlackBoardLink?,
        val getMultipleBlackBoardsLink: GetMultipleBlackBoardsLink?,
        val getSingleBoardLink: GetSingleBoardLink?
    )
}
