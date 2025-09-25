package edu.adf.profe.notificaciones

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import edu.adf.profe.MainMenuActivity
import edu.adf.profe.R

object Notificaciones {

    val NOTIFICATION_CHANNEL_ID = "UNO"
    val NOTIFICATION_CHANNEL_NAME = "CANAL_ADF"

    private fun crearCanalNotificacion (context:Context):NotificationChannel?
    {
        return null
    }

    fun lanzarNotificacion (context:Context)
    {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            var notificationChannel = crearCanalNotificacion(context)
            notificationManager.createNotificationChannel(notificationChannel!!)
        }

        //CREAMOS LA NOTIFICACIÓN
        var nb = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.outline_casino_24)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.emoticono_risa))
            .setContentTitle("BUENOS DÍAS")
            .setSubText("aviso")
            .setContentText("Vamos a ver fotos de perros")
            .setAutoCancel(true)//es para que cuando toque la noti, desaparezca
            .setDefaults(Notification.DEFAULT_ALL)

        val intentDestino = Intent(context, MainMenuActivity::class.java)
        //pendingIntent -- iNTENT "SECURIZADO" -- permite lanzar el intent, como si estuviera dentro de mi app
        val pendingIntent = PendingIntent.getActivity(context, 100,
            intentDestino, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE )
        nb.setContentIntent(pendingIntent)//asocio el intent a la notificación

        val notificacion = nb.build()

        //ADD PERMISOS
        notificationManager.notify(500, notificacion)

    }
}