package com.example.tlece_task.utils.extension

import android.content.Context
import android.widget.Toast
import com.example.tlece_task.utils.UiText
import es.dmoral.toasty.Toasty

fun Context.getResString(uiText: UiText): String = uiText.asString(this)

fun Context.normal(uiText: UiText) {
    Toasty.normal(this, getResString(uiText), Toast.LENGTH_LONG).show()
}

fun Context.success(uiText: UiText) {
    Toasty.success(this, getResString(uiText), Toast.LENGTH_SHORT, true).show()
}

fun Context.error(uiText: UiText) {
    Toasty.error(this, getResString(uiText), Toast.LENGTH_SHORT, true).show()
}

fun Context.warning(uiText: UiText) {
    Toasty.warning(this, getResString(uiText), Toast.LENGTH_SHORT, true).show()
}
