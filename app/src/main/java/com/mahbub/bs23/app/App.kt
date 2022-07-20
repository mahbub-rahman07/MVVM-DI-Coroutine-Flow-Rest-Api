package com.mahbub.bs23.app

import android.app.Application
import com.mahbub.bs23.BuildConfig
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