package com.example.testapp.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkUtil @Inject constructor(private val context: Context) {
    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}