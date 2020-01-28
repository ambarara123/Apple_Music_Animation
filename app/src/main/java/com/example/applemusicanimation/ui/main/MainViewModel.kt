package com.example.applemusicanimation.ui.main

import android.net.Uri
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applemusicanimation.utils.uris
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val player: SimpleExoPlayer,
    private val progressiveMediaSource: ProgressiveMediaSource.Factory
) : ViewModel() {
    private val _playWhenReady = MutableLiveData<Boolean>()
    val playWhenReady: LiveData<Boolean>
        get() = _playWhenReady

    private var songNumber = 0

    fun initialisePlayer(songNumber: Int) {
        this.songNumber = songNumber
        val uri = Uri.parse(uris[songNumber])
        val mediaSource = progressiveMediaSource
            .createMediaSource(uri)
        player.playWhenReady = true
        player.prepare(mediaSource, true, false)
    }

    fun playPauseToggle(isPlaying: Boolean) {
        _playWhenReady.postValue(isPlaying)
        player.playWhenReady = isPlaying
    }

    fun nextSong() {
        if (songNumber == 2) songNumber = 0
        else songNumber++

        initialisePlayer(songNumber)
    }

    fun previousSong() {
        if (songNumber == 0) songNumber = 2
        else songNumber--

        initialisePlayer(songNumber)
    }

    private fun startCountDownTimer(interval: Long){
        object : CountDownTimer(interval,1000){

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                player.stop(true)
            }

        }.start()
    }


}