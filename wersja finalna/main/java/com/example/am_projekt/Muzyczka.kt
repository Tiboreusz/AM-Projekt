package com.example.am_projekt

import android.app.Application
import android.media.MediaPlayer
import com.example.am_projekt.data.SettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Muzyczka : Application() {
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.muzyczka)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        val volumeFlow = SettingsDataStore.volumeFlow(this)
        CoroutineScope(Dispatchers.IO).launch {
            volumeFlow.collect {
                mediaPlayer.setVolume(it, it)
            }
        }
    }

    override fun onTerminate() {
        mediaPlayer.release()
        super.onTerminate()
    }

    fun setVolume(volume: Float) {
        mediaPlayer.setVolume(volume, volume)
    }
}

