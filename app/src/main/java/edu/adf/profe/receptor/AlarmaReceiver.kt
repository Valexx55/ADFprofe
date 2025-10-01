package edu.adf.profe.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import edu.adf.profe.Constantes
import edu.adf.profe.servicios.NumeroAleatorioService

class AlarmaReceiver : BroadcastReceiver() {

    //alarma receiver y iniciomovil tienen el mismo código
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(Constantes.ETIQUETA_LOG, "En AlarmaReceiver")
        //TODO lanzaremos un servicio en background
        val intentService = Intent(context, NumeroAleatorioService::class.java)
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
        Log.d(Constantes.ETIQUETA_LOG, "Servicio lanzado...")

    }
}