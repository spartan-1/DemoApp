package com.pnr.demoapp.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DemoAppModule
 */
@Module
class DemoAppModule(application: Application) {

    private val demoAppContext: Context

    init {
        demoAppContext = application
    }

    /**
     * provides DemoAppContext
     */
    @Provides
    @Singleton
    fun provideDemoAppContext(): Context {
        return demoAppContext
    }
}