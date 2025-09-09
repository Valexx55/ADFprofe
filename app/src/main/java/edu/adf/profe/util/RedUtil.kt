package edu.adf.profe.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * utilizamos este objeto para comprobar el estado de la conexión a internet
 */
object RedUtil {

    fun hayInternet (context: Context): Boolean{
        var hay = false

            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            hay =  (cm.activeNetwork !=null)

        return hay
    }

    fun hayWifi (context: Context): Boolean{
        var hayWifi = false

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val caps = cm.getNetworkCapabilities(cm.activeNetwork)
        hayWifi = caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false

        return hayWifi
    }
}