package com.example.tlece_task.model


import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("success") var success: Int? = null,
    @SerializedName("failure") var failure: Int? = null
)