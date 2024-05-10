package com.example.tlece_task.service

import android.util.Log
import com.example.tlece_task.base.helper.NotificationHelper
import com.example.tlece_task.utils.safeLet
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d("xxx", "title: ${remoteMessage.notification?.title}")
        Log.d("xxx", "body: ${remoteMessage.notification?.body}")

        remoteMessage.notification?.let { message ->
            safeLet(message.title, message.body) { title, body ->
                NotificationHelper.showNotification(
                    context = this, titleText = title, bodyText = body
                )
            }
        }
    }

    override fun onNewToken(token: String) {}
}