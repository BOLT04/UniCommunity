package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.toBoardCollection
import isel.pt.unicommunity.model.links.MyBoardsLink
import isel.pt.unicommunity.presentation.adapter.PartialBoardView
import isel.pt.unicommunity.repository.network.NavLinkRequest

class MyBoardsViewModel(
    private val myBoards: MyBoardsLink,
    private val app: UniCommunityApp
) : ViewModel(){

    val myboardsLD = ErrorHandlingMLD<List<PartialBoardView>, String>()

    fun getMyBoards(){

        val req = NavLinkRequest(
            myBoards,
            Response.Listener {
                myboardsLD.success(it.toBoardCollection().boards.map { board ->
                    PartialBoardView(
                        board.self,
                        board.name,
                        board.id,
                        board.description
                    )
                })
            },
            Response.ErrorListener {
                myboardsLD.error(it.message ?: it.localizedMessage)
            },
            app.email,
            app.password
        )

        app.queue.add(req)
    }


}
