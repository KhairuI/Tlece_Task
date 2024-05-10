package com.example.tlece_task.utils.extension

import android.app.Activity
import android.app.Dialog
import com.example.tlece_task.R
import com.example.tlece_task.base.BaseActivity

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
