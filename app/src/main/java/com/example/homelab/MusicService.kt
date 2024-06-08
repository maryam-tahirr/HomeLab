package com.example.homelab

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        //initiating
        mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic)
        mediaPlayer.isLooping = true
        Log.d("MusicService", "MusicService Created")
    }
    //turning on the music
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        intent?.getFloatExtra("VOLUME", -1f)?.let { volume ->
            if (volume >= 0) {
                setVolume(volume)
            }
        }
        mediaPlayer.start()
        return START_STICKY
        Log.d("MusicService", "MusicService Started")
        return START_STICKY
    }

    //called when the service is destroyed
    //turning of the music
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
        Log.d("MusicService", "MusicService Stopped")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    //setting the volume
    fun setVolume(volume: Float) {
        mediaPlayer.setVolume(volume, volume)
    }


}
