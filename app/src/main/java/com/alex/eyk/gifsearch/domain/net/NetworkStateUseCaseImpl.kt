package com.alex.eyk.gifsearch.domain.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_BLUETOOTH
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import com.alex.eyk.gifsearch.domain.net.NetworkState.OFFLINE
import com.alex.eyk.gifsearch.domain.net.NetworkState.ONLINE

class NetworkStateUseCaseImpl(
    private val appContext: Context
) : NetworkStateUseCase {

    override fun getState(): NetworkState {
        val connectivityManager = appContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetworkCapabilities = connectivityManager
            .getNetworkCapabilities(
                connectivityManager.activeNetwork
            ) ?: return OFFLINE

        val isOnline = activeNetworkCapabilities.hasAnyTransport(
            listOf(
                TRANSPORT_WIFI,
                TRANSPORT_CELLULAR,
                TRANSPORT_ETHERNET,
                TRANSPORT_BLUETOOTH
            )
        )
        return if (isOnline) ONLINE else OFFLINE
    }

    private fun NetworkCapabilities.hasAnyTransport(
        types: List<Int>
    ): Boolean {
        types.forEach {
            if (this.hasTransport(it)) {
                return true
            }
        }
        return false
    }
}
