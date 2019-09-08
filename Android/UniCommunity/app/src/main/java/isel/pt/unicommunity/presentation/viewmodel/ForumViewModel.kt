package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.toForumItemCollection
import isel.pt.unicommunity.model.inputdto.ForumInputDto
import isel.pt.unicommunity.model.links.GetMultipleForumItemsLink
import isel.pt.unicommunity.model.links.GetSingleForumLink
import isel.pt.unicommunity.presentation.adapter.PartialForumItemView
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkGetRequest

class ForumViewModel(private val app: UniCommunityApp,
                     private val getSingleForumLink: GetSingleForumLink) : ViewModel(){


    val forum = ErrorHandlingMLD<ForumInputDto, String>()

    val forumItems = ErrorHandlingMLD<List<PartialForumItemView>, String>()

    fun getForum(){
        val getBlackBoardRequest = BasicAuthNavLinkGetRequest(
            getSingleForumLink,
            Response.Listener {
                forum.success(it)
            },
            Response.ErrorListener {
                forum.error(it.message ?: it.localizedMessage )
            },
            app.email,
            app.password
        )

        app.queue.add(getBlackBoardRequest)
    }

    fun parseForumItems(forum : ForumInputDto){

        val multipleForumItemsLink = forum._links.getMultipleForumItemsLink ?: return

        val forumItemsViews = forum._embedded.embeddedForumItems?.map {

            val name = it.name
            val getSingleForumItemLink = it._links?.self

            if(name == null || getSingleForumItemLink == null){
                getForumItems(multipleForumItemsLink)
                return
            }
            else
                PartialForumItemView(
                    name,
                    it.author,
                    getSingleForumItemLink
                )
        }


        if(forumItemsViews != null)
            forumItems.success(forumItemsViews)
        else
            getForumItems(multipleForumItemsLink)
    }

    private fun getForumItems(getMultipleForumItemsLink: GetMultipleForumItemsLink) {

        val getMultipleForumItemsRequest = BasicAuthNavLinkGetRequest(
            getMultipleForumItemsLink,
            Response.Listener { collectionJson ->
                forumItems.success(
                    collectionJson.toForumItemCollection().forumItems.map {
                        PartialForumItemView(
                            it.name,
                            it.authorName,
                            it.self
                        )
                    }
                )
            },
            Response.ErrorListener {
                forumItems.error(it.message ?: it.localizedMessage)
            },
            app.email,
            app.password
        )

        app.queue.add(getMultipleForumItemsRequest)
    }









}