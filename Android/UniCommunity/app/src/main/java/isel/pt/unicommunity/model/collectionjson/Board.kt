package isel.pt.unicommunity.model.collectionjson

import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.Rels

import isel.pt.unicommunity.model.links.DeleteBoardLink
import isel.pt.unicommunity.model.links.EditBoardLink
import isel.pt.unicommunity.model.links.GetMultipleBoardsLink
import isel.pt.unicommunity.model.links.GetSingleBoardLink

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
                        getLink(Rels.DELETE_BOARD, ::DeleteBoardLink),
                        getLink(Rels.GET_MULTIPLE_BOARDS, ::GetMultipleBoardsLink)
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
        val deleteBoardLink: DeleteBoardLink?,
        val getMultipleBoardsLink: GetMultipleBoardsLink?
        // add member
        // remove member
    )
}