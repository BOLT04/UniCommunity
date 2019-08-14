package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.links.GetMultipleBoardsLink
import isel.pt.unicommunity.model.collectionjson.BoardCollection
import isel.pt.unicommunity.model.collectionjson.toBoardCollection
import isel.pt.unicommunity.repository.network.NavLinkRequest

class AllBoardsViewModel(val app: UniCommunityApp, val link: GetMultipleBoardsLink) : BoardsViewModel(){


    val allBoards : MutableLiveData<BoardCollection> = MutableLiveData()


    fun getAllBoards(function: () -> Unit) {
        val linkRequest = NavLinkRequest(
            link,
            Response.Listener {
                function()
                allBoards.value = it.toBoardCollection()
            },
            Response.ErrorListener { function()/*TODO do something here*/ },
            app.email,
            app.password
        )
        app.queue.add(linkRequest)
    }

}
