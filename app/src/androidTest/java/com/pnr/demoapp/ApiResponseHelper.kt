package com.pnr.demoapp

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * ApiResponseHelper
 */
class ApiResponseHelper {
    @Throws(Exception::class)
    fun convertIOStreamtoStr(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val inputAsString = inputStream.bufferedReader().use { it.readText() }
        bufferedReader.close()
        return inputAsString
    }

    /**
     * to get mock response data
     */
    @Throws(Exception::class)
    fun getMockResponseFromFile(context: Context, filePath: String): String {
        val stream: InputStream = context.resources.assets.open(filePath)
        val mockResponse = convertIOStreamtoStr(stream)
        stream.close()
        return mockResponse
    }
}