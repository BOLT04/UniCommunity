package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.links.HomeInputDto
import isel.pt.unicommunity.model.links.HomeLink
import isel.pt.unicommunity.repository.network.NavLinkGetRequest

class HomeViewModel(
    private val app : UniCommunityApp,
    private val homeLink: HomeLink
): ViewModel(){

    val homeLD = ErrorHandlingMLD<HomeInputDto,String>()

    fun fetchHome(){

        val req = NavLinkGetRequest(
            homeLink,
            Response.Listener {
                homeLD.success(it)
            },
            Response.ErrorListener {
                homeLD.error(it.message ?: it.localizedMessage)
            }
        )

        app.queue.add(req)
    }






}