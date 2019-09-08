package isel.pt.unicommunity.repository.fcm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessagingService
import isel.pt.unicommunity.presentation.activity.MainActivity
import android.app.NotificationChannel
import android.os.Build.VERSION_CODES.O
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build.VERSION_CODES.O
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import isel.pt.unicommunity.R


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val notification = remoteMessage?.notification
        sendMyNotification(notification?.title, notification?.body)
    }

    private fun sendMyNotification(title : String?, body : String?){

        val mNotificationManager: NotificationManager
                = (this as Context).getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(this.applicationContext, "notify_001")

        val bigText = NotificationCompat.BigTextStyle()
        bigText.setBigContentTitle(title)

        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
        mBuilder.setContentTitle(title)
        mBuilder.setContentText(body)
        mBuilder.priority = NotificationManager.IMPORTANCE_DEFAULT
        mBuilder.setStyle(bigText)

        val channelId = "UniCommunity"
        val channel = NotificationChannel(
            channelId,
            "UniCommunity title",
            NotificationManager.IMPORTANCE_HIGH
        )
        mNotificationManager.createNotificationChannel(channel)
        mBuilder.setChannelId(channelId)

        mNotificationManager.notify(0, mBuilder.build())
    }

}