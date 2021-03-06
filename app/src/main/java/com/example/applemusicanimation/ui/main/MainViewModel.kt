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
    private val progressiveMediaSourceFactory: ProgressiveMediaSource.Factory
) : ViewModel() {

    private val _isSleepMode = MutableLiveData<Boolean>()
    val isSleepMode: LiveData<Boolean>
        get() = _isSleepMode

    private val _currentVsTotalPairLiveData = MutableLiveData<Pair<Long, Long>>()
    val currentVsTotalPairLiveData: LiveData<Pair<Long, Long>>
        get() = _currentVsTotalPairLiveData

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
            val currentPosition = player.currentPosition * 100
            val duration = player.duration
            _currentVsTotalPairLiveData.postValue(Pair(currentPosition, duration))
            handler?.postDelayed(runnable!!, 1000)
        }
        handler?.postDelayed(runnable!!, 0)
    }

    fun setupPlayer(songNumber: Int) {
        this.songNumber = songNumber
        val uri = Uri.parse(uris[songNumber])
        val mediaSource = progressiveMediaSourceFactory
            .createMediaSource(uri)
        player.playWhenReady = true
        player.prepare(mediaSource, true, false)
        setupSeekBar()
    }

    fun playPauseToggle() {
        player.playWhenReady = !player.playWhenReady
    }

    fun nextSong() {
        if (songNumber == uris.size - 1) songNumber = 0
        else songNumber++

        setupPlayer(songNumber)
    }

    fun previousSong() {
        if (songNumber == 0) songNumber = uris.size - 1
        else songNumber--

        setupPlayer(songNumber)
    }

    fun toggleSleepMode() {
        if (!isSleepMode.value!!)
            startCountDownTimer()
        else
            stopCountDownTimer()
    }

    fun seekToPosition(position: Int){
        val newPosition = (player.duration*position)/100
        player.seekTo(newPosition)
    }

    private fun startCountDownTimer(interval: Long= 30000) {
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

    private fun stopCountDownTimer() {
        _isSleepMode.value = false
        countDownTimer?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        handler?.removeCallbacks(runnable!!)
        handler = null
        runnable = null
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        player.stop()
        player.release()
    }

}