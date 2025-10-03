package edu.adf.profe.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import edu.adf.profe.Constantes
import edu.adf.profe.MainMenuActivity
import edu.adf.profe.alarma.GestorAlarma
import edu.adf.profe.notificaciones.Notificaciones
import edu.adf.profe.servicios.NumeroAleatorioService

class InicioMovilReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d(Constantes.ETIQUETA_LOG, "En InicioMovil receiver")
        /*try {
            Notificaciones.lanzarNotificacion(context)

        }catch (e:Exception)
        {
            Log.e(Constantes.ETIQUETA_LOG, "errro al lanzar noti ", e)
        }*/

        val ficherop = context.getSharedPreferences("ajustes", Context.MODE_PRIVATE)
        if (ficherop.getBoolean("ALARMA", false))
        {
            Log.d(Constantes.ETIQUETA_LOG, "Alarma programada, lanzo servicio")
            val intentService = Intent(context, NumeroAleatorioService::class.java)
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                context.startForegroundService(intentService)
            } else {
                context.startService(intentService)
            }
            Log.d(Constantes.ETIQUETA_LOG, "Servicio lanzado...")
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "No lanzo servicio de chequeo...")
        }


    }
}