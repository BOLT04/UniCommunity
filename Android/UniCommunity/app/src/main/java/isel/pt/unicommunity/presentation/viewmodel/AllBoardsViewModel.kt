package isel.pt.unicommunity.presentation.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.to_refactor.small.SmallBoardItem
import isel.pt.unicommunity.model.webdto.rel_links.GetMultipleBoardsLink
import isel.pt.unicommunity.model.webdto.rel_links.BoardCollection
import isel.pt.unicommunity.model.webdto.rel_links.BoardFromCollectionJson
import isel.pt.unicommunity.repository.network.NavLinkRequest

class AllBoardsViewModel(val app: UniCommunityApp, val link: GetMultipleBoardsLink) : BoardsViewModel(){

    val liveData : MutableLiveData<List<SmallBoardItem>> = MutableLiveData()

    val allBoards : MutableLiveData<BoardCollection> = MutableLiveData()




    fun getAllBoards(function: () -> Unit) {
        val linkRequest = NavLinkRequest(
            link,
            Response.Listener {
                function()
                allBoards.value = BoardFromCollectionJson(it)
            },
            Response.ErrorListener { function()/*TODO do something here*/ },
            app.email,
            app.password
        )
        app.queue.add(linkRequest)
    }

    init {
        val a = mutableListOf(
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc"),
            SmallBoardItem("asc"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa"),
            SmallBoardItem("asc", "asdadsa")
        )

        Handler().postDelayed({ liveData.value= a }, 5000)



    }
}
