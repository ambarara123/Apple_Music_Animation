package com.example.applemusicanimation.di.module

import android.content.Context
import android.net.Uri
import com.example.applemusicanimation.MyApplication
import com.example.applemusicanimation.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: MyApplication): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideExoPlayer(context: Context): SimpleExoPlayer {
        return ExoPlayerFactory.newSimpleInstance(context)
    }

    @Singleton
    @Provides
    fun provideDataSourceFactory(context: Context): DataSource.Factory {
        return DefaultDataSourceFactory(context, "exoplayer")
    }

    @Singleton
    @Provides
    fun provideUri(context: Context): Uri {
        return Uri.parse(context.getString(R.string.song_url))
    }

    @Singleton
    @Provides
    fun provideMediaSource(uri: Uri, dataSourceFactory: DataSource.Factory): MediaSource {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }
}