package com.pnr.demoapp.di

import com.pnr.demoapp.application.DemoApp
import com.pnr.demoapp.di.module.DemoAppModule
import com.pnr.demoapp.di.module.RestApiUtilProvider
import com.pnr.demoapp.restclient.webservice.CountryInfoWebService
import com.pnr.demoapp.ui.screens.countryinfo.fragments.CountryInfoListFragment
import com.pnr.demoapp.ui.screens.countryinfo.viewmodels.CountryInfoViewModel
import com.pnr.demoapp.ui.screens.countryinfo.viewmodels.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DemoAppModule::class,
        RestApiUtilProvider::class,
        ViewModelModule::class
    ]
)
/**
 * DemoAppComponent Dagger2
 */
interface DemoAppComponent : AndroidInjector<DemoApp> {
    /**
     * injection in CountryInfoWebService
     */
    fun inject(countryInfoWebService: CountryInfoWebService)

    /**
     * injection in CountryInfoViewModel
     */
    fun inject(countryInfoViewModel: CountryInfoViewModel)

    /**
     * injection in CountryInfoListFragment
     */
    fun inject(countryInfoListFragment: CountryInfoListFragment)
}