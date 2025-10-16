package edu.adf.profe.notificacionesfirebase

import android.R
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import edu.adf.profe.Constantes
import edu.adf.profe.MainMenuActivity
import edu.adf.profe.util.LogUtil


//class MiNotificacionFirebaseService : FirebaseMessagingService()  {
class MiNotificacionFirebaseService : FirebaseMessagingService()  {

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)//dejamos una llamada al padre por si aca...
        Log.d(Constantes.ETIQUETA_LOG, " ${LogUtil.getLogInfo()} Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        //sendRegistrationToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()} ID mensaje = ${message.messageId}")
        Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()} TIPO mensaje = ${message.messageType}")
        super.onMessageReceived(message)

        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val title: String? = message.notification?.title
        val body: String? = message.notification?.body
        //val target: String? = data.get("target") // actividad a abrir, por ejemplo

        val intent: Intent = Intent(this, MainMenuActivity::class.java)

        //TODO probar si podemos gestionar la notificación cuando la app está en primer plano
        //cuando está en segundo plano, para que sí funciona y nos lleva a la Main Activity
        //asegurar que este método no se invoca cuando recibo un mensaje de datos en vez de una notificación

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "mi_canal_id")
                .setSmallIcon(R.drawable.ic_delete)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(33, notificationBuilder.build())
        //nm.notify(33, message.notification)


    }


}