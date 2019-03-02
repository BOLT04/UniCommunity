package pt.isel.unicommunityprototype

import android.app.Application
import android.util.Log
import pt.isel.unicommunityprototype.repository.Repository

class UniCommunityApplication : Application() {
    val TAG = "UniCommunityApplication"

    lateinit var repository: Repository
        private set

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate application")

        repository = Repository()
    }
}
