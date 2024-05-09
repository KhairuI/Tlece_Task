package com.example.tlece_task.ui

import com.example.tlece_task.base.BaseViewModel
import com.example.tlece_task.ui.delegate.VideoViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val videoViewModelDelegate: VideoViewModelDelegate
) : BaseViewModel(), VideoViewModelDelegate by videoViewModelDelegate