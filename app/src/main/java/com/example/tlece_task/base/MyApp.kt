package com.example.tlece_task.base

import android.app.Application
import com.example.tlece_task.utils.DataSourceUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            task.result?.let { token ->
                DataSourceUtils.setToken(applicationContext, token)
            }
        })

        super.onCreate()
    }
}