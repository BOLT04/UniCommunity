package isel.pt.unicommunity.presentation.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.inputdto.ForumItemInputDto
import isel.pt.unicommunity.model.links.CreateForumItemLink
import isel.pt.unicommunity.model.outputdto.ForumItemOutputDto
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkPostRequest

class CreateForumItemViewModel(
    private val app: UniCommunityApp,
    private val createForumItemLink: CreateForumItemLink
) : ViewModel(){

    val createForumItemLd = ErrorHandlingMLD<ForumItemInputDto, String>()

    fun createForumItem(title : String?, content : String?, anonymus : Boolean){

        if(title == null || title.isEmpty()) {
            createForumItemLd.error("The title is not optional")
            return
        }

        val req = BasicAuthNavLinkPostRequest(
            createForumItemLink,
            ForumItemOutputDto(title, content, anonymus),
            Response.Listener {
                createForumItemLd.success(it)
            },
            Response.ErrorListener {
                createForumItemLd.error(VolleyErrorHandler.getMessage(it))
            },
            app.email,
            app.password
        )

        app.queue.add(req)
    }











}
