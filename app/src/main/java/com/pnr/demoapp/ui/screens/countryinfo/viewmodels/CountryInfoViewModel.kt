package com.pnr.demoapp.ui.screens.countryinfo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnr.demoapp.model.CountryInfo
import com.pnr.demoapp.restclient.webservice.CountryInfoWebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Country information ViewModel
 */
class CountryInfoViewModel @Inject constructor(private val countryInfoWebService: CountryInfoWebService) : ViewModel(),
    CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private lateinit var countryInfoMutableLiveData: MutableLiveData<CountryInfo>

    /**
     * load Country information data
     */
    fun loadData(refreshRequired: Boolean): MutableLiveData<CountryInfo> {
        Timber.d("Viewmodel loadData")
        var initStatus = false
        if (!::countryInfoMutableLiveData.isInitialized) {
            countryInfoMutableLiveData = MutableLiveData()
            initStatus = true
        }
        this.launch(coroutineContext) {
            if (refreshRequired || initStatus) {
                loadDataFromWebService()
            } else {
                countryInfoMutableLiveData.postValue(countryInfoMutableLiveData.value)
            }
        }
        return countryInfoMutableLiveData
    }

    /**
     * Call webservice to load data
     */
    private suspend fun loadDataFromWebService() {
        val response = countryInfoWebService.getCountryInfo()
        response?.let {
            countryInfoMutableLiveData.postValue(response.body())
        } ?: run {
            Timber.d("errorrrrr")
            //sending existing list when network call fails
            countryInfoMutableLiveData.postValue(countryInfoMutableLiveData.value)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}