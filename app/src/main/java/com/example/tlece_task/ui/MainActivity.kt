package com.example.tlece_task.ui

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tlece_task.base.BaseActivity
import com.example.tlece_task.databinding.ActivityMainBinding
import com.example.tlece_task.model.VideoModel
import com.example.tlece_task.ui.adapter.VideoAdapter
import com.example.tlece_task.utils.Result
import com.example.tlece_task.utils.loading
import com.example.tlece_task.utils.with
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

    override fun initView() {

        videoAdapter = VideoAdapter()

        binding.listVideo.with(videoAdapter.apply {
            clicked { video ->
                video.videoUrl?.let { url ->
                    startActivity(VideoPlayActivity.getStartIntent(this@MainActivity, url))
                }
            }
        })

        binding.imgNotification.setOnClickListener { viewModel.sendNotification() }

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
                                Toast.makeText(
                                    this@MainActivity,
                                    result.error,
                                    Toast.LENGTH_SHORT
                                ).show()

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
                                Log.d("xxx", "Error: ${result.error}")
                                Toast.makeText(
                                    this@MainActivity,
                                    result.error,
                                    Toast.LENGTH_SHORT
                                ).show()

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
        videoAdapter.submitList(data)
    }
}