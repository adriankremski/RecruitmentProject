package com.github.snuffix.android.appzumi.ui

import android.app.Application
import android.content.Context
import com.github.snuffix.android.appzumi.ui.injection.ApplicationComponent
import com.github.snuffix.android.appzumi.ui.injection.DaggerApplicationComponent

class TheApplication : Application() {

    companion object {
        operator fun get(context: Context): TheApplication {
            return context.applicationContext as TheApplication
        }
    }

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
    }
}
