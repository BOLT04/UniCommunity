package isel.pt.unicommunity.presentation.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.google.firebase.messaging.FirebaseMessaging
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.collectionjson.BoardCollection
import isel.pt.unicommunity.model.collectionjson.toBoardCollection
import isel.pt.unicommunity.model.links.*
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkDeleteRequest
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkPostRequest
import isel.pt.unicommunity.repository.network.BasicAuthNavLinkGetRequest

class AllBoardsViewModel(val app: UniCommunityApp, val link: GetMultipleBoardsLink): ViewModel(){


    val allBoardsLd = ErrorHandlingMLD<BoardCollection, String>()

    fun getAllBoards() {
        val linkRequest = BasicAuthNavLinkGetRequest(
            link,
            Response.Listener {
                allBoardsLd.success(it.toBoardCollection())
            },
            Response.ErrorListener {
                allBoardsLd.error(it.message ?: it.localizedMessage)
            },
            app.email,
            app.password
        )
        app.queue.add(linkRequest)
    }



    fun subscribeToBoard(subscribeLink: SubscribeLink){

        val req = BasicAuthNavLinkPostRequest(
            subscribeLink,
            SubscribeOutputDto(  ),
            Response.Listener {
                val instance = FirebaseMessaging.getInstance()
                it.topics.forEach { topicName ->
                    instance.subscribeToTopic(topicName)
                        .addOnCompleteListener { task ->
                            var msg = "success"
                            if (!task.isSuccessful) {
                                msg = "failed"
                            }
                            Log.v("SUBSCRIBING", msg)
                            Toast.makeText(app, msg, Toast.LENGTH_SHORT).show()
                        }
                }
            },
            Response.ErrorListener {
                val a = it
            },
            app.email,
            app.password
        )

        app.queue.add(req)

    }

    fun unsubscribeFromBoard(unsubscribeLink: UnsubscribeLink){

        val req = BasicAuthNavLinkDeleteRequest(
            unsubscribeLink,
            Response.Listener {
                val instance = FirebaseMessaging.getInstance()
                it.topics.forEach { topicName ->
                    instance.unsubscribeFromTopic(topicName)
                        .addOnCompleteListener { task ->
                            var msg = "success"
                            if (!task.isSuccessful) {
                                msg = "failed"
                            }
                            Log.v("SUBSCRIBING", msg)
                            Toast.makeText(app, msg, Toast.LENGTH_SHORT).show()
                        }
                }
            },
            Response.ErrorListener {
                val a = it
            },
            app.email,
            app.password
        )

        app.queue.add(req)

    }

}

