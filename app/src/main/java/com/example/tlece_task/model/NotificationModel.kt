package com.example.tlece_task.model


import com.google.gson.annotations.SerializedName

data class NotificationModel(
     @SerializedName("body")  val body: String? = null,
     @SerializedName("title")  val title: String? = null
)