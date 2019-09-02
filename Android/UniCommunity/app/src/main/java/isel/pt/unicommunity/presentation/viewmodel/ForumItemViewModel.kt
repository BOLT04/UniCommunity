package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.inputdto.ForumItemInputDto
import isel.pt.unicommunity.model.links.GetSingleForumItemLink
import isel.pt.unicommunity.repository.network.NavLinkRequest

class ForumItemViewModel(
    private val app: UniCommunityApp,
    private val singleForumItemLink: GetSingleForumItemLink
) : ViewModel(){

    val forumItemLd = ErrorHandlingMLD<ForumItemInputDto, String>()

    fun fetchForumItem(){

        val request = NavLinkRequest(
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

}
