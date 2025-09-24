package edu.adf.profe.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import edu.adf.profe.Constantes
import edu.adf.profe.MainMenuActivity

class InicioMovilReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d(Constantes.ETIQUETA_LOG, "En InicioMovil receiver")
        val intentMainActivity = Intent(context, MainMenuActivity::class.java)
        intentMainActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intentMainActivity)//TODO REVISAR LOG LANZAMIENTO
    }
}