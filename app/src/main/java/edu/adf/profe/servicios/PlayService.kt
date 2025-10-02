package edu.adf.profe.servicios

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.notificaciones.Notificaciones
import edu.adf.profe.notificaciones.Notificaciones.NOTIFICATION_CHANNEL_ID
import edu.adf.profe.notificaciones.Notificaciones.NOTIFICATION_CHANNEL_ID2
import edu.adf.profe.notificaciones.Notificaciones.NOTIFICATION_CHANNEL_ID3
import edu.adf.profe.notificaciones.Notificaciones.NOTIFICATION_CHANNEL_NAME2
import edu.adf.profe.notificaciones.Notificaciones.NOTIFICATION_CHANNEL_NAME3
import edu.adf.profe.notificaciones.Notificaciones.crearCanalNotificacionForegroundService


class PlayService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d(Constantes.ETIQUETA_LOG, "Creando PlayService")
    }

    /**
     * Tanto este atributo (MediaPLayer, como los métodos relativos a eĺ
     * deberían ir en otra clase. Por motivos didácticos (no disgregar el código y
     * facilitar su seguimiento, se declaran aquí)
     */


    companion object {

        var mediaPlayer: MediaPlayer? = null
        var sonando: Boolean = false

        private fun play(context: Context?) {
            if (!sonando) {

                mediaPlayer = MediaPlayer.create(context, R.raw.audio)
                mediaPlayer!!.start()
                sonando = true
                mediaPlayer!!.setOnCompletionListener {
                    //detener el servicio y poner sonando a false
                    sonando = false
                    //Lanzo el intent para que se cierre el servicio
                    val stopIntent: Intent = Intent(context, PlayService::class.java)
                    stopIntent.setAction(Constantes.STOPFOREGROUND_ACTION)
                    context!!.startService(stopIntent)

                }
            }
        }

        private fun stop() {
            mediaPlayer!!.stop()
            sonando=false
            mediaPlayer=null
        }
    }


    //Usaremos este PendingIntent cuando el usuario Clique en la Notificacion (en ningún icono a la escucha)
    private fun obtenerNotificationIntent(): PendingIntent? {
        val notificationPendingIntent: PendingIntent? = null

        val notificationIntent: Intent = Intent(this, PlayActivity::class.java)
        notificationIntent.setAction(Constantes.MAIN_ACTION)
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        return notificationPendingIntent
    }


    fun obtenerPendingIntentActivity(): PendingIntent {
        var intentActivity: PendingIntent? = null

        val notificationIntent: Intent = Intent(this, PlayActivity::class.java)
        notificationIntent.setAction(Constantes.MAIN_ACTION)
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intentActivity = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        return intentActivity
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i(Constantes.ETIQUETA_LOG, "Entrando en foreground service ")

        val intent_action = intent.getAction()

        if (intent_action == Constantes.STARTFOREGROUND_ACTION) {
            Log.i(Constantes.ETIQUETA_LOG, "Han llamado al servio para lanzarlo ")


            //Intent notificationIntent = new Intent(this, MainActivity.class);
            //notificationIntent.setAction(Constantes.MAIN_ACTION);
            //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            val pendingIntent =
                obtenerPendingIntentActivity() //PendingIntent.getActivity(this, 0, notificationIntent, 0);


            // PendingIntent intentActivity = obtenerIntentActivity();
            /**
             * Obtengo e objeto Remote View, que conformora el Layout de la notificación
             * Debo emplear esta clase para que la notificación pueda ser mostrada por otro proceso
             * (y no el hilo principal)
             *
             * Cuando ejecuto un PlayService, no hay seguridad de que el servicio se ejecute
             * en un hilo separado o no. Por ello, debo emplear una RemoteView, para que pueda ser
             * mostrada aún por otro hilo
             *
             */
            val notificationView = RemoteViews(this.getPackageName(), R.layout.notification)


            /** El hecho de emplear RemoteViews, varía la forma de gestión de eventos:
             *
             * Para cada elemento visual al que quiera asociarle una acción al ser tocado en la pantalla,
             * no me vale el onClikListener - por ser una RemoteView -; sino que debo usar un PendingIntent
             * que a su vez (lo más cómodo) se invoque a un Reciever
             *
             */

            // Creo el PendingIntent para cuando se toque el boton PLAY y lo asocio a la correspondiente vista
            val buttonPlayIntent = Intent(this, NotificationPlayButtonHandler::class.java)
            val buttonPlayPendingIntent = PendingIntent.getBroadcast(
                this, 100, buttonPlayIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            notificationView.setOnClickPendingIntent(
                R.id.notification_button_play,
                buttonPlayPendingIntent
            )

            // Creo el PendingIntent para cuando se toque el boton Skip (siguiente) y lo asocio a la correspondiente vista
            val buttonSkipIntent = Intent(this, NotificationSkipButtonHandler::class.java)
            val buttonSkipPendingIntent = PendingIntent.getBroadcast(
                this, 100, buttonSkipIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            notificationView.setOnClickPendingIntent(
                R.id.notification_button_skip,
                buttonSkipPendingIntent
            )

            // Creo el PendingIntent para cuando se toque el boton Prev (anterior) y lo asocio a la correspondiente vista
            val buttonPrevIntent = Intent(this, NotificationPrevButtonHandler::class.java)
            val buttonPrevPendingIntent = PendingIntent.getBroadcast(this, 0, buttonPrevIntent,
                PendingIntent.FLAG_IMMUTABLE)
            notificationView.setOnClickPendingIntent(
                R.id.notification_button_prev,
                buttonPrevPendingIntent
            )


            // Creo el PendingIntent para cuando se toque el boton Close (cierre) y lo asocio a la correspondiente vista
            val buttonCloseIntent = Intent(this, NotificationCloseButtonHandler::class.java)
            val buttonClosePendingIntent = PendingIntent.getBroadcast(this, 0, buttonCloseIntent,
                PendingIntent.FLAG_IMMUTABLE)
            notificationView.setOnClickPendingIntent(
                R.id.notification_button_close,
                buttonClosePendingIntent
            )


            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val nc = Notificaciones.crearCanalNotificacionPrincipal(this)
                val notificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(nc!!) //creo nc si ya existe??
            }*/
            // nb = NotificationCompat.Builder(this, Notificaciones.NOTIFICATION_CHANNEL_ID)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                crearCanalNotificacionForegroundService (this, NOTIFICATION_CHANNEL_ID3, NOTIFICATION_CHANNEL_NAME3)

            }

            //Genero la Notificación
            val notification: Notification =
                NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID3)
                    .setContentTitle("Player segundo plano")
                    .setTicker("Player segundo plano")
                    .setContentText("Música maestro")
                    .setSmallIcon(R.mipmap.ic_launcher_round) //icono peque : not plegada
                    .setCustomContentView(notificationView)
                    //.setContent(notificationView) //la vista personalizada, con sus PendingIntentAsociados
                    .setContentIntent(pendingIntent) //la actividad a la que llamaremos si tocan la notificación
                    .build() // y se hace

            //lanzo el servicio haciendo visible la notificación
            //y la actividad de reproducción
            startForeground(103, notification)
            play(this)
        } else if (intent_action == Constantes.STOPFOREGROUND_ACTION) {
            Toast.makeText(this, "Parando servicio", Toast.LENGTH_SHORT).show()
            Log.i(Constantes.ETIQUETA_LOG, "Petición de parada recibida")

            //elimino el servicio del "foreground"
            stopForeground(STOP_FOREGROUND_REMOVE)
            //lo detengo
            stopSelf()
            //paro la música
            stop()
        }


        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(Constantes.ETIQUETA_LOG, "Destruyendo el Servicio")
        Toast.makeText(this, "Parando servicio", Toast.LENGTH_SHORT).show()
        Log.i(Constantes.ETIQUETA_LOG, "Petición de parada recibida")

        //elimino el servicio del "foreground"
        stopForeground(STOP_FOREGROUND_REMOVE)
        //lo detengo
        stopSelf()
        //paro la música
        stop()
    }

    override fun onBind(intent: Intent?): IBinder? {
        //devolvemos nul, ya que estamos implmentando un PlayService (No un bounded)
        return null
    }


    /**
     * Los siguientes Recievers están aquí, pero igualmente deberían estar aparte.
     *
     */
    /**
     * Reciever Invocado al tocar el boton play
     */

    class NotificationPlayButtonHandler : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "Play Seleccionado", Toast.LENGTH_SHORT).show()
            play(context)
        }
    }

    /**
     * Reciever Invocado al tocar el boton SKIP
     */
    class NotificationSkipButtonHandler : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "Siguiente Seleccionado", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Reciever Invocado al tocar el boton atrás (prev)
     */
    class NotificationPrevButtonHandler : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "Prev Seleccionado", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Reciever Invocado al tocar el boton cerrar (prev)
     */
    class NotificationCloseButtonHandler : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            Toast.makeText(context, "Close Seleccionado", Toast.LENGTH_SHORT).show()

            //Lanzo el intent para que se cierre el servicio
            /*val stopIntent: Intent = Intent(context, PlayService::class.java)
            stopIntent.setAction(Constantes.STOPFOREGROUND_ACTION)
            context.startService(stopIntent)*/
            //vamos a probar matar al serivicio desde fuera con el método
            val intent = Intent(context, PlayService::class.java)
            context.stopService(intent)
            Log.d(Constantes.ETIQUETA_LOG, "DETENGO SERVICIO nueva forma con stopService")
        }
    }
}