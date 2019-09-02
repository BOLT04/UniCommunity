package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.toCommentCollection
import isel.pt.unicommunity.model.links.GetMultipleCommentsLink
import isel.pt.unicommunity.presentation.adapter.PartialCommentView
import isel.pt.unicommunity.repository.network.NavLinkRequest


class CommentViewModel(
    private val app: UniCommunityApp,
    private val multipleCommentsLink: GetMultipleCommentsLink
) : ViewModel() {

    val comments = ErrorHandlingMLD<List<PartialCommentView>, String>()




    fun fetchComments(){

        val req = NavLinkRequest(
            multipleCommentsLink,
            Response.Listener {
                comments.success(
                    it.toCommentCollection().comments.map { pc ->
                        PartialCommentView(
                            pc.authorName,
                            pc.content,
                            pc.createdAt,
                            pc.self
                        )
                    }
                )


            },
            Response.ErrorListener {
                comments.error(it.message ?: it.localizedMessage)
            },
            app.email,
            app.password
        )

        app.queue.add(req)
    }

}
