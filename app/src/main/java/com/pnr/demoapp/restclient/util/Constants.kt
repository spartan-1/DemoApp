package com.pnr.demoapp.restclient.util

/**
 * Constants file to hold rest operations related constants
 */
class Constants {
    companion object {
        /**
         * Base Url
         */
        var BASE_URL = "https://dl.dropboxusercontent.com/"

        /**
         * URL to get country info data
         */
        const val COUNTRY_INFO_URL = "s/2iodh4vg0eortkl/facts.json"

        /**
         * api connect timeout
         */
        const val API_CONNECT_TIMEOUT_IN_SECONDS = 30L

        /**
         * api read timeout
         */
        const val API_READ_TIMEOUT_IN_SECONDS = 30L

        /**
         * api write timeout
         */
        const val API_WRITE_TIMEOUT_IN_SECONDS = 30L

        /**
         * cache size
         */
        const val CACHE_SIZE = 10 * 1024 * 1024L

        /**
         * HTTP status ok
         */
        const val HTTP_OK = 200

        /**
         * HTTP status not found
         */
        const val HTTP_NOT_FOUND = 404
    }
}