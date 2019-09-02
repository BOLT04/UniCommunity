package isel.pt.unicommunity.presentation.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.links.NavigationInputDto
import isel.pt.unicommunity.repository.network.BasicAuthenticationGetRequest

class MainActivityViewModel(val app:UniCommunityApp) : ViewModel(){

    lateinit var homeFragment : Fragment
    lateinit var allBoardsFragment: Fragment
    lateinit var myBoardsFragment: Fragment
    lateinit var profile: Fragment



    val navigation = ErrorHandlingMLD<NavigationInputDto, String>()


    fun fetchNav(url:String){
        val getRequest = BasicAuthenticationGetRequest(
            NavigationInputDto::class.java,
            url,
            Response.Listener { navigation.success(it) },
            Response.ErrorListener { navigation.error(VolleyErrorHandler.getMessage(it)) },
            app.email,
            app.password
        )

        app.queue.add(getRequest)
    }

    fun getStarter(starter : String): Fragment?{

        return when(starter){

            "Home" -> homeFragment //todo usar o resource com o titulo home

            else -> null
        }
    }

}