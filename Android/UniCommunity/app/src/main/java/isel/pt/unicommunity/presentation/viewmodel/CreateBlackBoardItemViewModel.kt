package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.links.CreateBlackBoardItemLink
import isel.pt.unicommunity.model.inputdto.BlackBoardItemInputDto
import isel.pt.unicommunity.model.outputdto.BlackBoardItemOutputDto
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkPostRequest

class CreateBlackBoardItemViewModel(val app: UniCommunityApp, val link: CreateBlackBoardItemLink) : ViewModel() {

    val createItemLd = ErrorHandlingMLD<BlackBoardItemInputDto, String>()

    fun postItem(
        title: String,
        content: String?
    ){

        val createBlackBoardItemRequest = BasicAuthNavLinkPostRequest(
            link,
            BlackBoardItemOutputDto(title, content),
            Response.Listener { createItemLd.success(it) },
            Response.ErrorListener { createItemLd.error( it.message ?: it.localizedMessage ) },
            app.email,
            app.password
        )

        app.queue.add(createBlackBoardItemRequest)
    }

}
