package com.example.applemusicanimation.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<B: ViewDataBinding,VM: ViewModel> : DaggerAppCompatActivity(){
    lateinit var viewModel: VM
    lateinit var binding: B

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
    }

    private fun bindView(){
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        viewModel = ViewModelProvider(this,viewModelFactory).get(getViewModelClass())
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModelClass(): Class<VM>

}