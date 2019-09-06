package isel.pt.unicommunity

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack


class UniCommunityApp : Application(){

    lateinit var messagingService: Intent
    val APP_NAME = "UniCommunity"
    val SharedPreferences_FileName = "UniCommunity_SharedPreferences"



    lateinit var queue : RequestQueue


    override fun onCreate() {
        super.onCreate()

        val cache = DiskBasedCache(this.cacheDir, 10 * 1024 * 1024) //10Mb cap
        val network = BasicNetwork(HurlStack())

         queue = RequestQueue(cache, network).apply {
            start()
        }
    }

    var username : String? = null
    lateinit var password : String
    lateinit var email : String

}