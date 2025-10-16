package edu.adf.profe.notificacionesfirebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import edu.adf.profe.Constantes
import edu.adf.profe.util.LogUtil


//class MiNotificacionFirebaseService : FirebaseMessagingService()  {
class MiNotificacionFirebaseService : FirebaseMessagingService()  {

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(Constantes.ETIQUETA_LOG, " ${LogUtil.getLogInfo()} Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        //sendRegistrationToServer(token)
    }
}