package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.inputdto.ForumItemInputDto
import isel.pt.unicommunity.model.inputdto.UserInputDto
import isel.pt.unicommunity.model.links.GetSingleForumItemLink
import isel.pt.unicommunity.model.links.GetSingleUserLink
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkGetRequest

class ForumItemViewModel(
    private val app: UniCommunityApp,
    private val singleForumItemLink: GetSingleForumItemLink
) : ViewModel(){

    val forumItemLd = ErrorHandlingMLD<ForumItemInputDto, String>()
    val userLd = ErrorHandlingMLD<UserInputDto, String>()

    fun fetchForumItem(){

        val request = BasicAuthNavLinkGetRequest(
            singleForumItemLink,
            Response.Listener {
                forumItemLd.success(it)
            },
            Response.ErrorListener {
                forumItemLd.error(VolleyErrorHandler.getMessage(it))
            },
            app.email,
            app.password
        )

        app.queue.add(request)
    }

    fun fetchUser(link : GetSingleUserLink){
        val request = BasicAuthNavLinkGetRequest(
            link,
            Response.Listener {
                userLd.success(it)
            },
            Response.ErrorListener {
                userLd.error(VolleyErrorHandler.getMessage(it))
            },
            app.email,
            app.password
        )

        app.queue.add(request)
    }

}
