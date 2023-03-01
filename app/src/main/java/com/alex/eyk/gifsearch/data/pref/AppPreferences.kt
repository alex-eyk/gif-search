package com.alex.eyk.gifsearch.data.pref

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(
    appContext: Context
) {

    companion object {

        const val PREF_NAME = "app_preferences"

        const val MAX_CACHE_SIZE_KEY = "max_cache_size"

        const val CACHE_FOR_RESPONSES = 0.1
        const val CACHE_FOR_GIFS = 1 - CACHE_FOR_RESPONSES

        private const val CACHE_SIZE_DEFAULT = 128
    }

    private val sharedPref: SharedPreferences

    init {
        this.sharedPref = appContext.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun putMaxCacheSize(
        sizeInMB: Int
    ) {
        sharedPref.edit()
            .putInt(MAX_CACHE_SIZE_KEY, sizeInMB)
            .apply()
    }

    fun getMaxCacheSizeInMB() = sharedPref.getInt(MAX_CACHE_SIZE_KEY, CACHE_SIZE_DEFAULT)
}
