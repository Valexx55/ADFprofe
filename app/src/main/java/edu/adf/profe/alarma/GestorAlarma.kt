package edu.adf.profe.alarma

import android.Manifest
import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import edu.adf.profe.Constantes
import edu.adf.profe.receptor.AlarmaReceiver
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * ME declaro un objeto en vez clase, cuando sólo necesito una instancia "es algo estático-JAVA"
 */
object GestorAlarma {

    //@RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun programarAlarma (context: Context)
    {
        //accedo al servicio del Sistema AlarmManager
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //calcular el tiempo donde suena la alarma
        val tiempo = System.currentTimeMillis()+(30*1000*2*15)//(30*1000)//30 segundos más

        //preparo el listener de la alarma - Receiver
        val intentAlarma = Intent(context, AlarmaReceiver::class.java)
        val pendingIntentAlarma = PendingIntent.getBroadcast(context, 303, intentAlarma,
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
}