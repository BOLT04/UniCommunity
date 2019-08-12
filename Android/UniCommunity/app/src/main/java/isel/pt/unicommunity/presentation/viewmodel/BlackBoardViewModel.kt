package isel.pt.unicommunity.presentation.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.to_refactor.small.SmallBlackBoardItem
import isel.pt.unicommunity.model.webdto.rel_links.*
import isel.pt.unicommunity.repository.network.NavLinkRequest

class BlackBoardViewModel(
    val app: UniCommunityApp,
    val blackBoardLink: GetSingleBlackBoardLink
) : ViewModel(){

    val liveData : MutableLiveData<List<SmallBlackBoardItem>> = MutableLiveData()

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
                    BlackBoardItemFromCollectionJson(collectionJson).blackboardItems.map {
                        BlackBoardItemRepr(it.name, it.content, it.createdAt, it.authorName, it.self)
                    }
            },
            Response.ErrorListener { },
            app.email,
            app.password
        )

        app.queue.add(navLinkRequest)
    }

    init {
        val a = mutableListOf(
            SmallBlackBoardItem("we in this"),
            SmallBlackBoardItem("amanha nao vou"),
            SmallBlackBoardItem("amanha vou duas vezes"),
            SmallBlackBoardItem("amanha eu vou e voces nao vao"),
            SmallBlackBoardItem("rak is best"),
            SmallBlackBoardItem("rak is waifu", "dont judge me please"),
            SmallBlackBoardItem("♥_♥"),
            SmallBlackBoardItem("rak as waifu forum", "forum")
        )

        Handler().postDelayed({ liveData.value= a }, 5000)



    }
}

class BlackBoardItemRepr(
    val title : String,
    val description : String?,
    val date : String,
    val author : String,
    val getSingleBlackBoardItemLink: GetSingleBlackBoardItemLink
)
