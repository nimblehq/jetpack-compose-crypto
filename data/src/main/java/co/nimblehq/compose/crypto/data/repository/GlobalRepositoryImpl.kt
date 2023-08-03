package co.nimblehq.compose.crypto.data.repository

import android.content.Context
import android.net.*
import co.nimblehq.compose.crypto.domain.repository.GlobalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GlobalRepositoryImpl(
    context: Context
) : GlobalRepository {

    private val isNetworkConnected: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isNetworkConnected.value = true
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            if (unmetered) {
                isNetworkConnected.value = true
            }
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            isNetworkConnected.value = false
        }
    }

    init {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    override fun isNetworkConnected(): Flow<Boolean?> = isNetworkConnected
}
