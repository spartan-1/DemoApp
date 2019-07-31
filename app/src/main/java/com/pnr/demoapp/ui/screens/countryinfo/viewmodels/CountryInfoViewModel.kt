package com.pnr.demoapp.ui.screens.countryinfo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnr.demoapp.model.CountryInfo
import com.pnr.demoapp.model.CountryInfoResponse
import com.pnr.demoapp.restclient.webservice.CountryInfoWebService
import com.pnr.demoapp.util.app.constants.ErrorType
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

    private lateinit var countryInfoMutableLiveData: MutableLiveData<CountryInfoResponse>

    /**
     * load Country information data
     */
    fun loadData(refreshRequired: Boolean, infiniteScroll: Boolean): MutableLiveData<CountryInfoResponse> {
        Timber.d("Viewmodel loadData")
        var initStatus = false
        if (!::countryInfoMutableLiveData.isInitialized) {
            countryInfoMutableLiveData = MutableLiveData()
            initStatus = true
        }
        this.launch(coroutineContext) {
            if (refreshRequired || initStatus || infiniteScroll) {
                loadDataFromWebService(refreshRequired)
            } else {
                countryInfoMutableLiveData.postValue(
                    CountryInfoResponse(
                        false, null,
                        countryInfoMutableLiveData.value!!.countryInfo
                    )
                )
            }
        }
        return countryInfoMutableLiveData
    }

    /**
     * Call webservice to load data
     */
    private suspend fun loadDataFromWebService(refreshRequired: Boolean) {
        val response = countryInfoWebService.getCountryInfo()
        response?.let {
            //handling success response
            if (response.isSuccessful && response.body() != null) {
                countryInfoMutableLiveData.value?.let {
                    val data: CountryInfo
                    if (refreshRequired) {
                        data = response.body() as CountryInfo
                    } else {
                        data = CountryInfo(
                            response.body()!!.title,
                            response.body()!!.rows + countryInfoMutableLiveData.value!!.countryInfo!!.rows
                        )
                    }
                    countryInfoMutableLiveData.postValue(CountryInfoResponse(true, null, data))
                } ?: run {
                    //This is for first time launch, livedata will be null on first launch
                    countryInfoMutableLiveData.postValue(
                        CountryInfoResponse(
                            true,
                            null,
                            response.body() as CountryInfo
                        )
                    )
                }
            } else {

                countryInfoMutableLiveData.postValue(
                    CountryInfoResponse(
                        false, ErrorType.NO_DATA,
                        countryInfoMutableLiveData.value!!.countryInfo
                    )
                )
            }
        } ?: run {
            Timber.d("error response")
            countryInfoMutableLiveData.value?.let {
                countryInfoMutableLiveData.postValue(
                    CountryInfoResponse(
                        false,
                        ErrorType.API_FAILURE,
                        countryInfoMutableLiveData.value!!.countryInfo
                    )
                )
            } ?: run {
                countryInfoMutableLiveData.postValue(CountryInfoResponse(true, null, null))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}