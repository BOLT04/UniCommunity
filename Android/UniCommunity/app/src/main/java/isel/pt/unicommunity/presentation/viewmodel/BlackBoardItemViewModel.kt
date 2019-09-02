package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.links.GetSingleBlackBoardItemLink
import isel.pt.unicommunity.model.inputdto.BlackBoardItemInputDto
import isel.pt.unicommunity.repository.network.NavLinkRequest

class BlackBoardItemViewModel(
    val app: UniCommunityApp,
    val link: GetSingleBlackBoardItemLink
) : ViewModel() {

    val blackboardItem = ErrorHandlingMLD<BlackBoardItemInputDto,String>()

    fun getBlackBoardItem() {
        val navLinkRequest = NavLinkRequest(
            link,
            Response.Listener { blackboardItem.success(it) },
            Response.ErrorListener { blackboardItem.error(it.message ?: it.localizedMessage) },
            app.email,
            app.password
        )
        app.queue.add(navLinkRequest)
    }

}
