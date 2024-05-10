package com.example.tlece_task.utils

import android.content.Context

object DataSourceUtils {

    fun setToken(context: Context, token: String) {
        val mPrefs = context.getSharedPreferences(AppConstants.DB_SHARED, Context.MODE_PRIVATE)
        mPrefs.edit().putString(AppConstants.KEY_TOKEN, token).apply()

    }

    fun getToken(context: Context): String? {
        val mPrefs = context.getSharedPreferences(AppConstants.DB_SHARED, Context.MODE_PRIVATE)
        return mPrefs.getString(AppConstants.KEY_TOKEN, "")
    }

}

