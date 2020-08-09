package com.kaustavjaiswal.omdbsampleapp.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Connection monitoring utility to detect availability of network connection
 */
object ConnectionUtils : ConnectivityManager.NetworkCallback() {

    private val connectionLiveData: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Returns instance of [LiveData] which can be observed for connection changes.
     */
    fun getConnectionLiveData(context: Context): LiveData<Boolean> {
        var connected = false

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), this)
        }

        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)

            networkCapability?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    connected = true
                    return@forEach
                }
            }
        }
        connectionLiveData.postValue(connected)
        return connectionLiveData
    }

    override fun onAvailable(network: Network) {
        connectionLiveData.postValue(true)
    }

    override fun onLost(network: Network) {
        connectionLiveData.postValue(false)
    }
}
