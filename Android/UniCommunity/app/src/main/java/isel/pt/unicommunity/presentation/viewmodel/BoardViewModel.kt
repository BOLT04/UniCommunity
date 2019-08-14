package isel.pt.unicommunity.presentation.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.toBlackBoardCollection
import isel.pt.unicommunity.model.inputdto.BoardInputDto
import isel.pt.unicommunity.model.links.GetMultipleBoardsLink
import isel.pt.unicommunity.model.links.GetSingleBlackBoardLink
import isel.pt.unicommunity.model.links.GetSingleBoardLink
import isel.pt.unicommunity.model.links.GetSingleForumLink
import isel.pt.unicommunity.repository.network.NavLinkRequest

class BoardViewModel(val app: UniCommunityApp, val getSingleBoardLink: GetSingleBoardLink) : ViewModel(){

    val board = MutableLiveData<BoardInputDto>()
    val menu = MutableLiveData<MenuView>()


    fun fetchBoard(){

        val navLinkRequest = NavLinkRequest(
            getSingleBoardLink,
            Response.Listener {
                board.value = it
            },
            Response.ErrorListener {
                val a = 1
                /*TODO something here*/
            },
            app.email,
            app.password
        )
        app.queue.add(navLinkRequest)
    }

    class MenuView(
        val blackBoards : List<EvenSmallerBlackBoard>,
        val forum : GetSingleForumLink?
    )

    class EvenSmallerBlackBoard(
        val name: String,
        val link: GetSingleBlackBoardLink
    )


    fun fetchModules(
        multipleBlackBoardsLink: GetMultipleBoardsLink?,
        singleForumLink: GetSingleForumLink?
    ) {

        if(multipleBlackBoardsLink!=null) {
            val getBlackBoards = NavLinkRequest(
                multipleBlackBoardsLink,
                Response.Listener { collectionJson ->
                    val blackBoards = collectionJson.toBlackBoardCollection()
                    menu.value = MenuView(
                        blackBoards.blackBoards.map {
                            EvenSmallerBlackBoard(it.name, it.self)
                        },
                        singleForumLink
                    )

                },
                Response.ErrorListener { },
                app.email,
                app.password
            )

            app.queue.add(getBlackBoards)
        }




    }




}