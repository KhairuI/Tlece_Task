package com.example.tlece_task.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tlece_task.utils.extension.hideProgress

abstract class BaseActivity : AppCompatActivity() {

    open var progress: Dialog? = null

    protected abstract fun getLayoutResourceId(): View?

    protected open fun initView(): Unit = Unit

    protected open fun observeViewModel(): Unit = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLayoutResourceId()?.let { setContentView(it) }
        initView()
        observeViewModel()
    }

    override fun onDestroy() {
        progress.hideProgress()
        super.onDestroy()
    }
}