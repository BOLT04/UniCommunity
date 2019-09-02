package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.links.GetMultipleBoardsLink
import isel.pt.unicommunity.model.collectionjson.BoardCollection
import isel.pt.unicommunity.model.collectionjson.toBoardCollection
import isel.pt.unicommunity.repository.network.NavLinkRequest

class AllBoardsViewModel(val app: UniCommunityApp, val link: GetMultipleBoardsLink): ViewModel(){


    val allBoardsLd = ErrorHandlingMLD<BoardCollection, String>()

    fun getAllBoards() {
        val linkRequest = NavLinkRequest(
            link,
            Response.Listener {
                allBoardsLd.success(it.toBoardCollection())
            },
            Response.ErrorListener {
                allBoardsLd.error(it.message ?: it.localizedMessage)
            },
            app.email,
            app.password
        )
        app.queue.add(linkRequest)
    }

}

class ErrorHandlingMLD<S,E>{

    private val success = MutableLiveData<S>()
    private val error = MutableLiveData<E>()

    fun observe(owner: LifecycleOwner, successListener: Observer<S>, errorListener: Observer<E>){
        success.observe(owner, successListener)
        error.observe(owner, errorListener)
    }

    fun success (value: S) { success.value = value}
    fun error (value: E) { error.value = value }

}