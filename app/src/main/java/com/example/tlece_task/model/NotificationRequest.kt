package com.example.tlece_task.model


import com.google.gson.annotations.SerializedName

data class NotificationRequest(
    @SerializedName("to")  val to: String? = null,
    @SerializedName("notification") val notification: NotificationModel? = null
)