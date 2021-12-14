package com.assessment.condenast

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        @get:Synchronized
        lateinit var application: MainApplication
            private set
    }
}