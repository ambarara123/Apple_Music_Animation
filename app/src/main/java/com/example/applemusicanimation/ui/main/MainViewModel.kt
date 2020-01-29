package com.example.applemusicanimation.ui.main

import android.net.Uri
import android.os.CountDownTimer
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applemusicanimation.utils.uris
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val player: SimpleExoPlayer,
    private val progressiveMediaSource: ProgressiveMediaSource.Factory
) : ViewModel() {
    private val _playWhenReady = MutableLiveData<Boolean>()
    val playWhenReady: LiveData<Boolean>
        get() = _playWhenReady

    private val _isSleepMode = MutableLiveData<Boolean>()
    val isSleepMode: LiveData<Boolean>
        get() = _isSleepMode

    private val _getCurrentPosition = MutableLiveData<Pair<Long, Long>>()
    val getCurrentPosition: LiveData<Pair<Long, Long>>
        get() = _getCurrentPosition

    private var countDownTimer: CountDownTimer? = null

    private var songNumber = 0

    private var handler: Handler? = null
    private var runnable: Runnable? = null

    init {
        _isSleepMode.value = false
    }

    private fun setupSeekBar() {
        handler = Handler()
        runnable = Runnable {
            val currentPosition = player.currentPosition*100
            val duration = player.duration
            _getCurrentPosition.postValue(Pair(currentPosition, duration))
            handler?.postDelayed(runnable!!, 1000)
        }
        handler?.postDelayed(runnable!!, 0)
    }

    fun setupPlayer(songNumber: Int) {
        this.songNumber = songNumber
        val uri = Uri.parse(uris[songNumber])
        val mediaSource = progressiveMediaSource
            .createMediaSource(uri)
        player.playWhenReady = true
        player.prepare(mediaSource, true, false)
        setupSeekBar()
    }

    fun playPauseToggle(isPlaying: Boolean) {
        _playWhenReady.postValue(isPlaying)
        player.playWhenReady = isPlaying
    }

    fun nextSong() {
        if (songNumber == 2) songNumber = 0
        else songNumber++

        setupPlayer(songNumber)
    }

    fun previousSong() {
        if (songNumber == 0) songNumber = 2
        else songNumber--

        setupPlayer(songNumber)
    }

    fun startCountDownTimer(interval: Long) {
        _isSleepMode.value = true
        countDownTimer = object : CountDownTimer(interval, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                Timber.d("${player.currentPosition} / ${player.duration}")
            }

            override fun onFinish() {
                _isSleepMode.value = false
                player.stop(true)
            }

        }.start()
    }

    fun stopCountDownTimer() {
        _isSleepMode.value = false
        countDownTimer?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        player.stop()
        player.release()
    }

}