package com.example.applemusicanimation

import android.os.Bundle
import com.example.applemusicanimation.databinding.ActivityMainBinding
import com.example.applemusicanimation.ui.MainViewModel
import com.example.applemusicanimation.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
