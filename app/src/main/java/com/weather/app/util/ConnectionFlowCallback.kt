package com.weather.app.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class ConnectionFlowCallback(context: Context) {

    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()


    @ExperimentalCoroutinesApi
    fun startNetworkCallback() = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                launch(Dispatchers.IO) {
                    val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                    if (hasInternet) {
                        withContext(Dispatchers.Main) {
                            validNetworks.add(network)
                            send(true)
                        }
                    }
                }
            }

            override fun onLost(network: Network) {
                launch(Dispatchers.Main) {
                    validNetworks.remove(network)
                    send(false)
                }
            }
        }

        cm.registerNetworkCallback(
            NetworkRequest.Builder().addCapability(NET_CAPABILITY_INTERNET).build(),
            networkCallback
        )

        awaitClose { cm.unregisterNetworkCallback(networkCallback) }
    }
}
