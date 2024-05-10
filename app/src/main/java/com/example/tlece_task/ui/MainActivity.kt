package com.example.tlece_task.ui

import android.os.Build
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tlece_task.base.BaseActivity
import com.example.tlece_task.databinding.ActivityMainBinding
import com.example.tlece_task.model.VideoModel
import com.example.tlece_task.ui.adapter.VideoAdapter
import com.example.tlece_task.utils.PermissionUtils
import com.example.tlece_task.utils.Result
import com.example.tlece_task.utils.extension.error
import com.example.tlece_task.utils.extension.loading
import com.example.tlece_task.utils.extension.with
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var videoAdapter: VideoAdapter

    override fun getLayoutResourceId(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {

        videoAdapter = VideoAdapter()

        binding.listVideo.with(videoAdapter.apply {
            clicked { video ->
                video.videoUrl?.let { url ->
                    startActivity(VideoPlayActivity.getStartIntent(this@MainActivity, url))
                }
            }
        })

        binding.imgNotification.setOnClickListener {
            if (PermissionUtils.checkNotificationPermission(this)) {
                viewModel.sendNotification()
            } else {
                requestStoragePermissions.launch(
                    arrayOf(PermissionUtils.NOTIFICATION_PERMISSION)
                )
            }
        }

        // get video list
        viewModel.getVideoList()
    }


    override fun observeViewModel() {

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.videoListResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                error(result.uiText)
                            }

                            is Result.Loading -> {
                                loading(result.isLoading)
                            }

                            is Result.Success -> {
                                parseData(result.data)
                            }
                        }
                    }
                }

                launch {
                    viewModel.sendNotificationResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                error(result.uiText)
                            }

                            is Result.Loading -> {}

                            is Result.Success -> {}
                        }
                    }
                }
            }
        }

    }

    private fun parseData(data: VideoModel) {
        if (!data.isEmpty()) {
            binding.tvNoData.visibility = View.INVISIBLE
            videoAdapter.submitList(data)
        }

    }

    private val requestStoragePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            if (permission[PermissionUtils.NOTIFICATION_PERMISSION] == true) {
                viewModel.sendNotification()
            } else {
                Log.d("xxx", "Permission Not Granted")
            }

        }
}