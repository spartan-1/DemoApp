package com.pnr.demoapp.model

import com.pnr.demoapp.util.app.constants.ErrorType

/**
 * data class CountryInfo
 */
data class CountryInfoResponse(
    val responseUpdated:Boolean,
    val errorStatus: ErrorType?,
    val countryInfo: CountryInfo?
)