package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.webdto.rel_links.BlackBoardItemInputDto
import isel.pt.unicommunity.model.webdto.rel_links.GetSingleBlackBoardItemLink
import isel.pt.unicommunity.repository.network.NavLinkRequest

class BlackBoardItemViewModel(
    val app: UniCommunityApp,
    val link: GetSingleBlackBoardItemLink
) : ViewModel() {

    val blackboardItem : MutableLiveData<BlackBoardItemInputDto> = MutableLiveData()

    fun getBlackBoardItem() {
        val navLinkRequest = NavLinkRequest(
            link,
            Response.Listener { blackboardItem.value = it },
            Response.ErrorListener {  },
            app.email,
            app.password
        )
        app.queue.add(navLinkRequest)
    }

}
