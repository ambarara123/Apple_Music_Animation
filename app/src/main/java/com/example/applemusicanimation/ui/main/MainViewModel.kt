package com.example.applemusicanimation.ui.main

import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import javax.inject.Inject

class MainViewModel @Inject constructor(val player: SimpleExoPlayer,val mediaSource: MediaSource): ViewModel()