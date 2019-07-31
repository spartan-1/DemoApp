package com.pnr.demoapp.restclient.webservice

import com.pnr.demoapp.model.CountryInfo
import com.pnr.demoapp.restclient.interfaces.ApiService
import dagger.Module
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

/**
 * CountryInfoWebService
 */
@Module
class CountryInfoWebService @Inject constructor(private var apiService: ApiService) {

    suspend fun getCountryInfo(): Response<CountryInfo>? {
        try {
            return apiService.getCountryInfo()
        } catch(e:Exception){
            //when there is no internet connection
            return null
        }
    }
}