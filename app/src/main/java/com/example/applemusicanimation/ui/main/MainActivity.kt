package com.example.applemusicanimation.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.lifecycle.Observer
import com.example.applemusicanimation.R
import com.example.applemusicanimation.databinding.ActivityMainBinding
import com.example.applemusicanimation.ui.base.BaseActivity
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),SeekBar.OnSeekBarChangeListener {


    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onClickListener()
        addObservers()
    }

    private fun addObservers() {
        viewModel.isSleepMode.observe(this, Observer {
            binding.sleepButton.setBackgroundResForSleepMode(it)
        })

        viewModel.currentVsTotalPairLiveData.observe(this, Observer {
            binding.seekBar.setProgress((it.first / it.second).toInt(), true)
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickListener() {

        with(binding) {
            first.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    viewModel.setupPlayer(0)
                    Timber.d("clicked")
                }
                return@setOnTouchListener false
            }
            nextButton.setOnClickListener { viewModel.nextSong() }

            previousButton.setOnClickListener { viewModel.previousSong() }

            pauseButton.setOnClickListener { viewModel.playPauseToggle() }

            sleepButton.setOnClickListener { viewModel.toggleSleepMode() }

            seekBar.setOnSeekBarChangeListener(this@MainActivity)
        }
    }

    private fun ImageButton.setBackgroundResForSleepMode(isSleepMode: Boolean) {
        if (isSleepMode)
            setBackgroundResource(
                R.drawable.ic_timer_off_black_24dp
            )
        else
            setBackgroundResource(
                R.drawable.ic_timer_black_24dp
            )
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser)
            viewModel.seekToPosition(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }
}
