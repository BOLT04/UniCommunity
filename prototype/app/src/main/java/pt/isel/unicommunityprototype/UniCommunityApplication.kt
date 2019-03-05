package pt.isel.unicommunityprototype

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import pt.isel.unicommunityprototype.common.NotificationGenerator
import pt.isel.unicommunityprototype.repository.Repository
import pt.isel.unicommunityprototype.repository.data_access.FirestoreIntermediator

class UniCommunityApplication : Application() {
    val TAG = "UniCommunityApplication"

    lateinit var repository: Repository
        private set
    lateinit var firestore: FirestoreIntermediator
        private set

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate application")

        FirebaseApp.initializeApp(this)
        firestore = FirestoreIntermediator(this, NotificationGenerator(this))

        repository = Repository(firestore)
    }
}
