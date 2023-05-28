package com.pixabay.imagesearch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject


class Network @Inject constructor(private val context: Context) : NetworkConnectivity {
    override fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}

interface NetworkConnectivity {
    fun isNetworkAvailable(): Boolean
}