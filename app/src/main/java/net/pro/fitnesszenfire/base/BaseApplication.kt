package net.pro.fitnesszenfire.base

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        FacebookSdk.sdkInitialize(applicationContext)
        FacebookSdk.fullyInitialize()
        FacebookSdk.setAutoInitEnabled(true)
        AppEventsLogger.activateApp(this)
    }
}