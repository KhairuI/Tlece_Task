package com.example.tlece_task.utils

import android.content.Context
import androidx.annotation.StringRes
import com.example.tlece_task.R

sealed class UiText {
    data class DynamicString(val value: String?) : UiText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value ?: context.getString(R.string.api_default_error)
            is StringResource -> context.getString(resId, *args)
        }
    }
}
