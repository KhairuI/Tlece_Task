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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

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
            clicked {

            }
        })

        // get video list
        viewModel.getVideoList()
        tickerFlow(5.seconds)
    }

    override fun observeViewModel() {

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.videoListResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                Log.d("xxx", "Error: ${result.error}")
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
            }
        }

    }

    private fun parseData(data: VideoModel) {
        videoAdapter.submitList(data)
    }

    private fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
        delay(initialDelay)
        while (true) {
            Log.d("xxx", "tickerFlow: ")
            emit(Unit)
            delay(period)
        }
    }
}