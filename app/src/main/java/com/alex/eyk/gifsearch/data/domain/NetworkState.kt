package com.alex.eyk.gifsearch.data.domain

sealed class NetworkState {

    object ONLINE : NetworkState()

    object OFFLINE : NetworkState()
}
