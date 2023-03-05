package com.alex.eyk.gifsearch

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GifSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG_MODE) {
            val threadPolicy = StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            StrictMode.setThreadPolicy(threadPolicy)
            val vmPolicy = StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            StrictMode.setVmPolicy(vmPolicy)
        }
    }
}
