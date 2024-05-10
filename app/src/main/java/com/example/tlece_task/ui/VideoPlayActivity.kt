package com.example.tlece_task.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.media3.exoplayer.ExoPlayer
import com.example.tlece_task.base.BaseActivity
import com.example.tlece_task.databinding.ActivityVideoPlayBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayActivity : BaseActivity() {

    private lateinit var binding: ActivityVideoPlayBinding
    private lateinit var player: ExoPlayer

    companion object {
        private const val KEY_URL = "key_url"

        fun getStartIntent(context: Context, url: String): Intent =
            Intent(context, VideoPlayActivity::class.java).apply {
                putExtra(KEY_URL, url)
            }
    }


    override fun getLayoutResourceId(): View {
        binding = ActivityVideoPlayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {

        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

        var url = ""
        intent.extras?.getString(KEY_URL)?.let {
            url = it
        }

        if (url == "") {
            Toast.makeText(this,"Unable to play this video",Toast.LENGTH_SHORT).show()
        } else {

            val mediaItem = androidx.media3.common.MediaItem.fromUri(url)
            player.apply {
                setMediaItem(mediaItem)
                prepare()
                play()
            }
        }


    }

    override fun onStart() {
        player.playWhenReady = true
        super.onStart()
    }

    override fun onStop() {
        player.playWhenReady = false
        super.onStop()
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }
}