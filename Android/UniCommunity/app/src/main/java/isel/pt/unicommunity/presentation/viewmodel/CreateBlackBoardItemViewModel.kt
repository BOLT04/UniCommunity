package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.webdto.rel_links.BlackBoardItemInputDto
import isel.pt.unicommunity.model.webdto.rel_links.BlackBoardItemOutputDto
import isel.pt.unicommunity.model.webdto.rel_links.CreateBlackBoardItemLink
import isel.pt.unicommunity.repository.network.NavLinkPostRequest

class CreateBlackBoardItemViewModel(val app: UniCommunityApp, val link: CreateBlackBoardItemLink) : ViewModel() {

    fun postItem(
        title: String,
        content: String?,
        success : Response.Listener<BlackBoardItemInputDto>,
        error : Response.ErrorListener
    ){

        val createBlacBoardItemRequest = NavLinkPostRequest(
            link,
            BlackBoardItemOutputDto(title, content),
            success,
            error,
            app.email,
            app.password
        )

        app.queue.add(createBlacBoardItemRequest)

    }

}
