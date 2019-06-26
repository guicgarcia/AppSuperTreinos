package com.example.t_gamer.supertreinos.app.user

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class UserApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        app = this
        appContext = applicationContext
    }

    companion object {
        lateinit var app: UserApplication
            private set
        lateinit var appContext: Context
            private set
    }

    val networkAvailable: Boolean
        get() {
            val connectivityManager =
                getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
}