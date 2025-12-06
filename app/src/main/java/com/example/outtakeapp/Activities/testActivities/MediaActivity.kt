package com.example.outtakeapp.Activities.testActivities

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import com.example.outtakeapp.Activities.BaseActivity
import com.example.outtakeapp.R
import com.example.outtakeapp.databinding.ActivityMediaAcvivityBinding
import androidx.core.net.toUri


class MediaActivity : BaseActivity() {
    lateinit var binding: ActivityMediaAcvivityBinding

    private val mediaPlayer = MediaPlayer()
    private fun initMedia() {
        try {
            val assets = assets
            val fd = assets.openFd("GoodSong.mp3")
            mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            Log.e("MediaActivity", "Error initializing media player", e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMediaAcvivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = ("android.resource://" + packageName + "/" + R.raw.video).toUri()
        binding.videoView.setVideoURI(uri)
        initMedia()
        clickVoice()
        clickVideo()
    }

    private fun clickVideo() {
        binding.playVideo.setOnClickListener {
            if (!binding.videoView.isPlaying){
                binding.videoView.start()
            }
        }
        binding.pauseVideo.setOnClickListener {
            if (binding.videoView.isPlaying){
                binding.videoView.pause()
            }
        }
        binding.stopVideo.setOnClickListener {
            if (binding.videoView.isPlaying){
                binding.videoView.stopPlayback()
            }
        }
    }

    private fun clickVoice() {
        binding.play.setOnClickListener {
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
            }
        }
        binding.pause.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }
        }
        binding.stop.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.stop()
                mediaPlayer.reset()
                initMedia()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()//释放
        binding.videoView.suspend()//释放
    }
}