package com.example.applemusicanimation.ui.main

import android.os.Bundle
import com.example.applemusicanimation.R
import com.example.applemusicanimation.databinding.ActivityMainBinding
import com.example.applemusicanimation.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onClickListener()
    }

    private fun onClickListener() {
        with(binding) {
            first.setOnClickListener {

            }
        }
    }
}
