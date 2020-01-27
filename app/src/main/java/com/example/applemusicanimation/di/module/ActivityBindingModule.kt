package com.example.applemusicanimation.di.module

import com.example.applemusicanimation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule{
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}