package com.alex.eyk.gifsearch.domain.net

sealed class NetworkState {

    object ONLINE : NetworkState()

    object OFFLINE : NetworkState()
}
