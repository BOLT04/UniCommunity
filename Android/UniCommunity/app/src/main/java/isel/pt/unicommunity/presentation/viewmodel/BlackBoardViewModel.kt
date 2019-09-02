package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.toBlackBoardItemCollection
import isel.pt.unicommunity.model.inputdto.BlackBoardInputDto
import isel.pt.unicommunity.model.links.GetMultipleBlackBoardItemsLink
import isel.pt.unicommunity.model.links.GetSingleBlackBoardItemLink
import isel.pt.unicommunity.model.links.GetSingleBlackBoardLink
import isel.pt.unicommunity.presentation.adapter.BlackBoardItemView
import isel.pt.unicommunity.repository.network.NavLinkRequest

class BlackBoardViewModel(
    val app: UniCommunityApp,
    val blackBoardLink: GetSingleBlackBoardLink
) : ViewModel(){

    val blackBoardLd = ErrorHandlingMLD<BlackBoardInputDto, String>()
    val blackBoardItemsLd = ErrorHandlingMLD<List<BlackBoardItemView>, String>()




    fun getBlackBoard(){
        val getBlackBoardRequest = NavLinkRequest(
            blackBoardLink,
            Response.Listener {
                blackBoardLd.success(it)
            },
            Response.ErrorListener {
                blackBoardLd.error(it.message ?: it.localizedMessage)
            },
            app.email,
            app.password
        )

        app.queue.add(getBlackBoardRequest)
    }

    fun getBlackBoardItems(getMultipleBlackBoardItemsLink: GetMultipleBlackBoardItemsLink?){
        if(getMultipleBlackBoardItemsLink!=null){
            val navLinkRequest = NavLinkRequest(
                getMultipleBlackBoardItemsLink,
                Response.Listener { collectionJson ->
                    blackBoardItemsLd.success(
                        collectionJson.toBlackBoardItemCollection().blackboardItems.map {
                            BlackBoardItemView(it.name, it.createdAt, it.authorName, it.self)
                        }
                    )
                },
                Response.ErrorListener {
                    blackBoardItemsLd.error(it.message ?: it.localizedMessage)
                },
                app.email,
                app.password
            )

            app.queue.add(navLinkRequest)
        }
    }

    fun fillWithEmbedded(
        smallBlackBoardItems: Array<BlackBoardInputDto.EmbeddedStruct.PartialBlackBoardItem>,
        multipleBlackBoardItemsLink: GetMultipleBlackBoardItemsLink?
    ) {

        val bbItems = smallBlackBoardItems.map {

            val name = it.name
            val author = it.author
            val date = it.date

            if (name == null || author == null || date == null) {
                getBlackBoardItems(multipleBlackBoardItemsLink)
                return
            }

            BlackBoardItemView(
                name,
                date,
                author,
                it._links.self
            )
        }

        blackBoardItemsLd.success(bbItems)
    }





}