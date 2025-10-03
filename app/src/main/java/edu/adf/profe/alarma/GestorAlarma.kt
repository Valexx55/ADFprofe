package edu.adf.profe.alarma

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import edu.adf.profe.Constantes
import edu.adf.profe.receptor.AlarmaReceiver
import java.text.SimpleDateFormat

/**
 * ME declaro un objeto en vez clase, cuando sólo necesito una instancia "es algo estático-JAVA"
 */
object GestorAlarma {

    const val ID_PROCESO_ALARMA = 8; //Id necesario para tener controlado cuando una alarma se programa y poder acceder a su cancelanción

    //@RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun programarAlarma (context: Context)
    {
        //accedo al servicio del Sistema AlarmManager
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //calcular el tiempo donde suena la alarma
        val tiempo = System.currentTimeMillis()+(60*1000)//(30*1000)//30 segundos más

        //preparo el listener de la alarma - Receiver
        val intentAlarma = Intent(context, AlarmaReceiver::class.java)
        val pendingIntentAlarma = PendingIntent.getBroadcast(context, ID_PROCESO_ALARMA, intentAlarma,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        //programo la alarma AlarmManager.RTC_WAKEUP --> TIEMPO EN MS DEL RELOJ DEL SISTEMA Y QUE SALTE CON EL STA BLOQUEADO
        //alarmManager.set(AlarmManager.RTC_WAKEUP, tiempo, pendingIntentAlarma)
        try {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, tiempo, pendingIntentAlarma);
            //alarmManager.set(AlarmManager.RTC_WAKEUP, tiempo, pendingIntentAlarma)
            //mostrarmos un mensaje informativo
            val dateformatter = SimpleDateFormat("E dd/MM/yyyy ' a las ' hh:mm:ss")
            val mensaje = dateformatter.format(tiempo)
            Log.d(Constantes.ETIQUETA_LOG, "ALARMA PROGRAMADA PARA $mensaje")
            Toast.makeText(context, "Alarma programada para $mensaje", Toast.LENGTH_LONG).show()
        } catch (e: Exception)
        {
            Log.e(Constantes.ETIQUETA_LOG, "ERROR AL PGROAMAR ALRMA EXACTA", e)
        }



    }

    fun desprogramarAlarma(context: Context) {

        val intentAlarma = Intent(context, AlarmaReceiver::class.java)
        val pendingIntentAlarma = PendingIntent.getBroadcast(context, ID_PROCESO_ALARMA, intentAlarma,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntentAlarma)

        Log.d(Constantes.ETIQUETA_LOG, "Alarma ANULADA")
    }

}