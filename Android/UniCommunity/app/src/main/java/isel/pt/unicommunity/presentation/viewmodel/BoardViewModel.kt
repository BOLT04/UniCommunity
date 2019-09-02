package isel.pt.unicommunity.presentation.viewmodel

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
import isel.pt.unicommunity.presentation.adapter.old.MenuView
import isel.pt.unicommunity.repository.network.NavLinkRequest

class BoardViewModel(val app: UniCommunityApp, val getSingleBoardLink: GetSingleBoardLink) : ViewModel(){

    val board = MutableLiveData<BoardInputDto>()
    val menu = MutableLiveData<MenuView>()

    val boardLd = ErrorHandlingMLD<BoardInputDto, String>()
    val menuLd = ErrorHandlingMLD<MenuView, String>()


    fun fetchBoard(){

        val navLinkRequest = NavLinkRequest(
            getSingleBoardLink,
            Response.Listener {
                boardLd.success(it)
            },
            Response.ErrorListener {
                boardLd.error(it.message ?: it.localizedMessage)
            },
            app.email,
            app.password
        )
        app.queue.add(navLinkRequest)
    }



    class PartialBlackBoardView(
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
                    menuLd.success(
                        MenuView(
                            blackBoards.blackBoards.map {
                                PartialBlackBoardView(it.name, it.self)
                            },
                            singleForumLink
                        )
                    )
                },
                Response.ErrorListener {
                    menuLd.error( it.message ?: it.localizedMessage)
                },
                app.email,
                app.password
            )

            app.queue.add(getBlackBoards)
        }
    }

    fun fillModules(
        smallBlackBoards: Array<BoardInputDto.EmbeddedStruct.PartialBlackBoardInputDto>,
        board: BoardInputDto
    ) {

        val menuView = MenuView(

            smallBlackBoards.map {
                val name = it.name
                val link = it._links.self

                if (name == null || link == null) {
                    fetchModules(board._links.getMultipleBlackBoardsLink, board._links.getSingleForumLink)
                    return
                }

                PartialBlackBoardView(name, link)

            },
            board._links.getSingleForumLink
        )

        menuLd.success(menuView)

    }




}