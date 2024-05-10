package com.example.tlece_task.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.example.tlece_task.R
import com.example.tlece_task.base.BaseActivity

fun Dialog?.fullscreen() = this?.window?.apply {
    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setLayout(
        /* width = */ WindowManager.LayoutParams.MATCH_PARENT,
        /* height = */ WindowManager.LayoutParams.MATCH_PARENT
    )
}

fun Dialog?.wrap() = this?.window?.apply {
    setLayout(
        /* width = */ 9 * context.resources.displayMetrics.widthPixels / 10,
        /* height = */ WindowManager.LayoutParams.WRAP_CONTENT
    )
}

fun Dialog?.ration(width: Double, height: Double) = this?.window?.apply {
    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setLayout(
        context.resources.displayMetrics.widthPixels.times(width).toInt(),
        context.resources.displayMetrics.heightPixels.times(height).toInt()
    )
}

fun Dialog?.hideProgress() {
    this?.let { dialog -> if (dialog.isShowing) dialog.cancel() }
}

fun Activity.showProgress(): Dialog? {
    if (isFinishing) return null

    val dialog = Dialog(this)
    dialog.show()
    dialog.window?.setBackgroundDrawableResource(R.color.transparent)
    dialog.setContentView(R.layout.progress_dialog)
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    return dialog
}

fun BaseActivity.loading(isLoading: Boolean = true) {
    when {
        isLoading -> {
            progress.hideProgress()
            progress = showProgress()
        }

        else -> progress.hideProgress()
    }
}
