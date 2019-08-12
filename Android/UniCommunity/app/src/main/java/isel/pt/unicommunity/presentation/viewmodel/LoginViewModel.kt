package isel.pt.unicommunity.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.webdto.rel_links.LoginInputDto
import isel.pt.unicommunity.model.webdto.rel_links.LoginOutputDto
import isel.pt.unicommunity.repository.network.PostRequest

class LoginViewModel(private val app : UniCommunityApp) : ViewModel() {

    val loginIsOk = MutableLiveData<LoginInputDto>()


    fun tryLogin(
        url: String,
        email:String,
        password:String,
        success :Response.Listener<LoginInputDto>,
        error : Response.ErrorListener
    ){

        val loginRequest = PostRequest(
            LoginInputDto::class.java,
            url,
            LoginOutputDto(email, password),
            success,
            error
        )

        app.queue.add(loginRequest)
    }

}
