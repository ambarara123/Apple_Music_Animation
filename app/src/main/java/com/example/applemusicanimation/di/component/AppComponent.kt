package com.example.applemusicanimation.di.component

import com.example.applemusicanimation.MyApplication
import com.example.applemusicanimation.di.module.ActivityBindingModule
import com.example.applemusicanimation.di.module.AppModule
import com.example.applemusicanimation.di.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ActivityBindingModule::class, ViewModelModule::class, AndroidInjectionModule::class])
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<MyApplication>
}