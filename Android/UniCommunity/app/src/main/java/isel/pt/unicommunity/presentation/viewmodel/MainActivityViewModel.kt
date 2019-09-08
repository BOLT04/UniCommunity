package isel.pt.unicommunity.presentation.viewmodel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import isel.pt.unicommunity.UniCommunityApp
import isel.pt.unicommunity.model.links.NavigationInputDto
import isel.pt.unicommunity.presentation.activity.SplashActivity
import isel.pt.unicommunity.repository.network.BasicAuthenticationGetRequest

class MainActivityViewModel(val app:UniCommunityApp) : ViewModel(){

    lateinit var reportsFragment : Fragment
    lateinit var allBoardsFragment: Fragment
    lateinit var myBoardsFragment: Fragment



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


    fun logout(activity : AppCompatActivity){

        val edit =
            activity.getSharedPreferences(app.sharedPreferencesFilename, Context.MODE_PRIVATE).edit()

        edit.putBoolean("isLoggedIn", false)
        edit.apply()

        app.email = ""
        app.password = ""
        app.stopService(app.messagingService)

        val intent = Intent(activity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)

    }

}