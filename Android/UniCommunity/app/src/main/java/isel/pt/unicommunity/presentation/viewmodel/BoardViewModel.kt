package isel.pt.unicommunity.presentation.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.to_refactor.small.SmallModule
import isel.pt.unicommunity.model.webdto.rel_links.*
import isel.pt.unicommunity.repository.network.NavLinkRequest

class BoardViewModel(val app: UniCommunityApp, val getSingleBoardLink: GetSingleBoardLink) : ViewModel(){

    val board = MutableLiveData<BoardInputDto>()
    val menu = MutableLiveData<MenuView>()


    val liveData : MutableLiveData<List<SmallModule>> = MutableLiveData()

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
                    val blackBoards = blackBoardFromCollectionJson(collectionJson)
                    menu.value = MenuView(
                        blackBoards.blackBoards.map {
                            EvenSmallerBlackBoard(it.name, GetSingleBlackBoardLink(it.href))
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

    init {
        val a = mutableListOf(
            SmallModule("anuncios"),
            SmallModule("trabalhos"),
            SmallModule("notas"),
            SmallModule("kebabs"),
            SmallModule("rak"),
            SmallModule("bananas"),
            SmallModule("pen"),
            SmallModule("pineapple"),
            SmallModule("uh"),
            SmallModule("pineapple-pen"),
            SmallModule("rak as waifu forum", "forum")
        )

        Handler().postDelayed({ liveData.value= a }, 5000)



    }


}