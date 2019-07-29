package com.pnr.demoapp.application

import android.app.Application
import com.pnr.demoapp.BuildConfig
import com.pnr.demoapp.di.DaggerDemoAppComponent
import com.pnr.demoapp.di.DemoAppComponent
import com.pnr.demoapp.di.module.DemoAppModule
import timber.log.Timber

/**
 * App class
 */
class DemoApp : Application() {

    private lateinit var demoAppComponent: DemoAppComponent

    override fun onCreate() {
        super.onCreate()
        demoAppComponent = DaggerDemoAppComponent.builder()
            .demoAppModule(DemoAppModule(this))
            .build()

        //Starting Timber to log in DEBUG mode
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * Function to get DI DemoAppComponent
     */
    fun getApplicationComponent(): DemoAppComponent {
        return demoAppComponent
    }
}