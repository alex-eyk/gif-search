package com.alex.eyk.gifsearch.data.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_BLUETOOTH
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import com.alex.eyk.gifsearch.data.domain.NetworkState.OFFLINE
import com.alex.eyk.gifsearch.data.domain.NetworkState.ONLINE

class NetworkStateUseCaseImpl(
    private val appContext: Context
) : NetworkStateUseCase {

    override fun getState(): NetworkState {
        val connectivityManager = appContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val network = connectivityManager.activeNetwork
            ?: return OFFLINE
        val actNw = connectivityManager.getNetworkCapabilities(network)
            ?: return OFFLINE
        return when {
            actNw.hasTransport(TRANSPORT_WIFI) -> ONLINE
            actNw.hasTransport(TRANSPORT_CELLULAR) -> ONLINE
            actNw.hasTransport(TRANSPORT_ETHERNET) -> ONLINE
            actNw.hasTransport(TRANSPORT_BLUETOOTH) -> ONLINE
            else -> OFFLINE
        }
    }
}
