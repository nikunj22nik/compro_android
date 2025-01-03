package com.yesitlab.compro

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize global state here
    }


}