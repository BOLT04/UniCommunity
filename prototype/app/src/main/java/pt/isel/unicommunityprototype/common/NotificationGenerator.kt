package pt.isel.unicommunityprototype.common

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import pt.isel.unicommunityprototype.R
import pt.isel.unicommunityprototype.UniCommunityApplication

const val CHANNEL_ID = "Announcements"
class NotificationGenerator(private val app: UniCommunityApplication) {

    var notificationId = 1 // TODO: do we need to save these? -> we have to if "you want to update or remove the notification."
    //TODO: document
    fun showNotification(title: String, text: String) {
        var builder = NotificationCompat.Builder(app, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_bg_normal)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with (NotificationManagerCompat.from(app)) {
            notify(notificationId++, builder.build())
        }
    }
}
