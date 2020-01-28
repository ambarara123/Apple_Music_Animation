package com.example.applemusicanimation.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.lifecycle.Observer
import com.example.applemusicanimation.R
import com.example.applemusicanimation.databinding.ActivityMainBinding
import com.example.applemusicanimation.ui.base.BaseActivity
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onClickListener()
        addObservers()
    }

    private fun addObservers(){
        viewModel.playWhenReady.observe(this, Observer {isPlaying->
            binding.pauseButton.setOnClickListener {
                viewModel.playPauseToggle(isPlaying)
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClickListener() {
        with(binding) {
            first.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP){
                    viewModel.initialisePlayer()
                    Timber.d("clicked")
                }
                return@setOnTouchListener false
            }

        }
    }
}
