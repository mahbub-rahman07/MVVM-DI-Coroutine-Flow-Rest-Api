package com.mahbub.rest_mvvm.app

import android.app.Application
import com.mahbub.rest_mvvm.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
        if(BuildConfig.DEBUG){
            Timber.plant( Timber.DebugTree())
        }
    }
}