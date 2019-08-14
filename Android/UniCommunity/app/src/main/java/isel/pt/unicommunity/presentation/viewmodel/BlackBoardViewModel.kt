package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.toBlackBoardItemCollection
import isel.pt.unicommunity.model.inputdto.BlackBoardInputDto
import isel.pt.unicommunity.model.links.GetMultipleBlackBoardItemsLink
import isel.pt.unicommunity.model.links.GetSingleBlackBoardItemLink
import isel.pt.unicommunity.model.links.GetSingleBlackBoardLink
import isel.pt.unicommunity.repository.network.NavLinkRequest

class BlackBoardViewModel(
    val app: UniCommunityApp,
    val blackBoardLink: GetSingleBlackBoardLink
) : ViewModel(){


    val blackBoard : MutableLiveData<BlackBoardInputDto> = MutableLiveData()
    val blackBoardItems : MutableLiveData<List<BlackBoardItemRepr>> = MutableLiveData()


    fun getBlackBoard(){
        val getBlackBoardRequest = NavLinkRequest(
            blackBoardLink,
            Response.Listener {
                blackBoard.value = it
            },
            Response.ErrorListener {
                val a = 1
                //todo
            },
            app.email,
            app.password
        )

        app.queue.add(getBlackBoardRequest)
    }

    fun getBlackBoardItems(getMultipleBlackBoardItemsLink: GetMultipleBlackBoardItemsLink){
        val navLinkRequest = NavLinkRequest(
            getMultipleBlackBoardItemsLink,
            Response.Listener { collectionJson ->
                blackBoardItems.value =
                    collectionJson.toBlackBoardItemCollection().blackboardItems.map {
                        BlackBoardItemRepr(it.name, it.content, it.createdAt, it.authorName, it.self)
                    }
            },
            Response.ErrorListener { },
            app.email,
            app.password
        )

        app.queue.add(navLinkRequest)
    }



    class BlackBoardItemRepr(
        val title : String,
        val description : String?,
        val date : String,
        val author : String,
        val getSingleBlackBoardItemLink: GetSingleBlackBoardItemLink
    )
}