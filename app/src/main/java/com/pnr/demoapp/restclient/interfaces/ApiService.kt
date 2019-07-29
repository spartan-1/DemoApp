package com.pnr.demoapp.restclient.interfaces

import com.pnr.demoapp.model.CountryInfo
import com.pnr.demoapp.restclient.util.Constants
import retrofit2.Response
import retrofit2.http.GET

/**
 * ApiService interface
 */
interface ApiService {

    /**
     * to get CountryInfo data
     */
    @GET(Constants.COUNTRY_INFO_URL)
    suspend fun getCountryInfo(): Response<CountryInfo>
}