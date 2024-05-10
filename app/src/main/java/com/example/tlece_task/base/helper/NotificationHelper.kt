package com.example.tlece_task.base.helper

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.tlece_task.R

object NotificationHelper {

    private const val requestCode = 1
    private const val channelName = "Push Notification"
    private const val notifyId = 0

    @SuppressLint("MissingPermission")
    fun showNotification(
        context: Context,
        titleText: String,
        bodyText: String
    ) {
        try {

            val channelId = context.getString(R.string.app_name)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder =
                NotificationCompat.Builder(context, channelId).setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(titleText).setContentText(bodyText).setAutoCancel(true)
                    .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                    .setLights(ContextCompat.getColor(context, R.color.text_color), 1000, 1000)
                    .setSound(defaultSoundUri)

            val notificationManager = NotificationManagerCompat.from(context)
            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_HIGH
                )
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(notifyId, notificationBuilder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}