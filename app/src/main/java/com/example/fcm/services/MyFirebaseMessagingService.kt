package com.example.fcm.services

import android.app.*
import android.content.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.fcm.MainActivity
import com.example.fcm.R
import com.google.firebase.messaging.*
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println("New Token: $token")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(rm: RemoteMessage) {
        val title = rm.notification?.title.toString()
        val body = rm.notification?.body.toString()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startOwnForeground(title, body)
        } else {
            startForeground(
                resources.getString(R.string.channel_id).toInt(),
                getNotification(title, body)
            )
        }
    }

    private fun getNotification(title: String, body: String): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        return NotificationCompat.Builder(this)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentIntent(pendingIntent)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startOwnForeground(title: String, body: String) {
        val channel = NotificationChannel(
            resources.getString(R.string.channel_id),
            resources.getString(R.string.channelName),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(channel)

        createNotification(title, body)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(title: String, body: String) {

        val notificationBuilder =
            NotificationCompat.Builder(this, resources.getString(R.string.channel_id))
        val notification = notificationBuilder
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)

        val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.notify(Random.nextInt(), notification.build())
    }
}