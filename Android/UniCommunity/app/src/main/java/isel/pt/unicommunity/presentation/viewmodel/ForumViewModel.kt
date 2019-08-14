package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.toForumItemCollection
import isel.pt.unicommunity.model.inputdto.ForumInputDto
import isel.pt.unicommunity.model.links.GetMultipleForumItemsLink
import isel.pt.unicommunity.model.links.GetSingleForumItemLink
import isel.pt.unicommunity.model.links.GetSingleForumLink
import isel.pt.unicommunity.repository.network.NavLinkRequest

class ForumViewModel(val app: UniCommunityApp, val getSingleForumLink: GetSingleForumLink) : ViewModel(){


    val forum = MutableLiveData<ForumInputDto>()

    val forumItems = MutableLiveData<List<PartialForumItemView>>()

    fun getForum(){
        val getBlackBoardRequest = NavLinkRequest(
            getSingleForumLink,
            Response.Listener {
                forum.value = it
            },
            Response.ErrorListener {
                val a = 1
                //todo
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
            forumItems.value = forumItemsViews
        else
            getForumItems(multipleForumItemsLink)
    }

    fun getForumItems(getMultipleForumItemsLink: GetMultipleForumItemsLink) {

        val getMultipleForumItemsRequest = NavLinkRequest(
            getMultipleForumItemsLink,
            Response.Listener { collectionJson ->
                forumItems.value = collectionJson.toForumItemCollection().forumItems.map {
                    PartialForumItemView(
                        it.name,
                        it.authorName,
                        it.self
                    )
                }
            },
            Response.ErrorListener {
                val a = 1
                //todo
            },
            app.email,
            app.password
        )

        app.queue.add(getMultipleForumItemsRequest)
    }


    class PartialForumItemView(
        val name: String,
        val author : String?,
        val getSingleForumItemLink: GetSingleForumItemLink
    )






}