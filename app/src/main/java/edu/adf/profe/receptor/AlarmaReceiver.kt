package edu.adf.profe.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import edu.adf.profe.Constantes

class AlarmaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(Constantes.ETIQUETA_LOG, "En AlarmaReceiver")
        //TODO lanzaremos un servicio en background

    }
}