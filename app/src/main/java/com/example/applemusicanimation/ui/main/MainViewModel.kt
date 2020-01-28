package com.example.applemusicanimation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import javax.inject.Inject

class MainViewModel @Inject constructor(private val player: SimpleExoPlayer, private val mediaSource: MediaSource) :
    ViewModel() {
    private val _playWhenReady = MutableLiveData<Boolean>()
    val playWhenReady : LiveData<Boolean>
    get() = _playWhenReady

    fun initialisePlayer() {
        player.playWhenReady = true
        player.prepare(mediaSource,false,false)
    }

    fun playPauseToggle(isPlaying: Boolean){
        _playWhenReady.value = isPlaying
        player.playWhenReady = isPlaying
    }
}