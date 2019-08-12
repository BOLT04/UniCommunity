package isel.pt.unicommunity

import android.app.Application
import androidx.lifecycle.ViewModel
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import isel.pt.unicommunity.model.to_refactor.linker.NavSupplier


class UniCommunityApp : Application(){

    val APP_NAME = "UniCommunity"
    val SharedPreferences_FileName = "UniCommunity_SharedPreferences"



    lateinit var queue : RequestQueue
    lateinit var navSupplier : NavSupplier


    override fun onCreate() {
        super.onCreate()

        val cache = DiskBasedCache(this.cacheDir, 10 * 1024 * 1024) //10Mb cap
        val network = BasicNetwork(HurlStack())

         queue = RequestQueue(cache, network).apply {
            start()
        }

        navSupplier = NavSupplier(this, mock = true)

    }

    var username : String? = null
    lateinit var password : String
    lateinit var email : String






    /*val TAG = "YAMAApplication"


    lateinit var repository: YAMARepository
    lateinit var chatBoard: ChatBoard
        private set

    lateinit var imageLoader: ImageLoader
    lateinit var workManager: WorkManager

    override fun registerActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks?) {
        Log.v("$TAG::actlog",callback?.javaClass.toString())
        Log.v("$TAG::actlog",callback?.toString())
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate application")



        queue.start()


        imageLoader= ImageLoader(queue,
            object : ImageLoader.ImageCache {
                private val localCache = LruCache<String, Bitmap>(20)

                override fun getBitmap(url: String): Bitmap? {
                    Log.v("URL_TAG", url)
                    Log.v("URL_TAG", Thread.currentThread().id.toString())
                    return localCache.get(url)
                }

                override fun putBitmap(url: String, bitmap: Bitmap) {
                    localCache.put(url, bitmap)
                }
            })

        val localDb =
            Room.databaseBuilder(this, YAMADatabase::class.java, "YAMA_db")
                .build()

        FirebaseApp.initializeApp(this)
        chatBoard = ChatBoard(this)

        repository = YAMARepository(this, GithubApi(this), localDb, FirebaseDatabase(chatBoard))
        workManager = WorkManager.getInstance()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        // Create notification channel if we are running on a O+ device
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                TEAM_NOTIFICATION_CHANNEL_ID,
                getString(R.string.teams_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = getString(R.string.teams_channel_description)
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

*/
}