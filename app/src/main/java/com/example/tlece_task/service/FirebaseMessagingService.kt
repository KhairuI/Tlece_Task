package com.example.tlece_task.service

import com.example.tlece_task.base.helper.NotificationHelper
import com.example.tlece_task.utils.extension.safeLet
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

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