package edu.adf.profe.notificaciones

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import edu.adf.profe.Constantes
import edu.adf.profe.MainMenuActivity
import edu.adf.profe.R

object Notificaciones {

    val NOTIFICATION_CHANNEL_ID = "UNO"
    val NOTIFICATION_CHANNEL_NAME = "CANAL_ADF"

    //Con estas anotaciones, puedo usar cosas de la versión indicada dentro de la función sin preocuparme de la versión mínima
    //Además, con Requires valida que la función llamante gestione/asegure la versión correcta
    //Por contra, con Target no valida que la función llamante gestione/asegure la versión correcta y deja llamar sin comprobarlo
    //@TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    fun crearCanalNotificacion ( context: Context
    ): NotificationChannel?
    {
        var notificationChannel : NotificationChannel? = null


        notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT )
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        //vibración patron suena 500 ms, no vibra otros 500 ms
        notificationChannel.vibrationPattern = longArrayOf(
            500,
            500,
            500,
            500,
            500,
            500,
        )
        notificationChannel.lightColor = context.applicationContext.getColor(R.color.mirojo)
        //sonido de  la notificación si el api es inferior a la 8, hay que setear el sonido aparte
        //si es igual o superior, la notificación "hereda" el sonido del canal asociado
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        notificationChannel.setSound(
            Uri.parse("android.resource://" + context.packageName + "/" + R.raw.snd_noti),
            audioAttributes
        )

        notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

        return notificationChannel
    }

    fun lanzarNotificacion (context:Context)


    {

        Log.d(Constantes.ETIQUETA_LOG, "Lanzando notificación ...")

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
            .setContentText("Vamos a entrar en ADF app")
            .setAutoCancel(true)//es para que cuando toque la noti, desaparezca
            .setDefaults(Notification.DEFAULT_ALL)

        val intentDestino = Intent(context, MainMenuActivity::class.java)
        //pendingIntent -- iNTENT "SECURIZADO" -- permite lanzar el intent, como si estuviera dentro de mi app
        val pendingIntent = PendingIntent.getActivity(context, 100,
            intentDestino, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE )
        nb.setContentIntent(pendingIntent)//asocio el intent a la notificación
        //si estoy en api anteriore, debo setea el sonido fuera porque no hay canal

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        {
            nb.setSound(Uri.parse
                ("android.resource://" + context.packageName + "/" + R.raw.snd_noti))
        }

        val notificacion = nb.build()

        //ADD PERMISOS
        notificationManager.notify(500, notificacion)

    }

    fun crearNotificacionSegundoPlano(context: Context): Notification {
        var segundo_plano: Notification? = null
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var nb: NotificationCompat.Builder? = null


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nc = crearCanalNotificacion( context)
            notificationManager.createNotificationChannel(nc!!) //creo nc si ya existe??
        }
        nb = NotificationCompat.Builder(context, Notificaciones.NOTIFICATION_CHANNEL_ID)

        nb.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        nb.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        nb.setSmallIcon(R.drawable.outline_arrow_circle_down_24) //importante blanco y fondo transparente
        nb.setContentTitle("Comprobando si hay mensajes")
        nb.setAutoCancel(true)
        nb.setDefaults(Notification.DEFAULT_ALL)
        nb.setTimeoutAfter(5000)


        segundo_plano = nb.build()
        Log.d("MIAPP", "Notificacion segundo plano creada")

        return segundo_plano
    }




}