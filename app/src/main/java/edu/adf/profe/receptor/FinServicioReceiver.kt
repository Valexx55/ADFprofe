package edu.adf.profe.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import edu.adf.profe.Constantes
import edu.adf.profe.alarma.GestorAlarma
import edu.adf.profe.notificaciones.Notificaciones

class FinServicioReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(Constantes.ETIQUETA_LOG, "Servicio finalizado")
        //innspecciono el intent para ver el resultado del servicico (número aleatorio)
        val numAleatorio = intent.getIntExtra("NUM_ALEATORIO", -1)
        Log.d(Constantes.ETIQUETA_LOG, "Número aleatorio = $numAleatorio")
        //si el número aleatorio ers >=60 --> "hay mensajes" -- lanzamos una noti
        //si no, -- no hago nada
        if (numAleatorio>=60)
        {
            Notificaciones.lanzarNotificacion(context)
            Log.d(Constantes.ETIQUETA_LOG, "El servicio nos da un número mayor a 60 , hay mensajes")
        }
        ///reprogramo la alarma
        GestorAlarma.programarAlarma(context)
        Log.d(Constantes.ETIQUETA_LOG, "Reprogramo alarma")
        //desregistrar el receptor, para que la proxima vez que acaba el servicio, no salte este objeto otra vez
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this)

    }
}