package edu.adf.profe.descargarcanciones

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import edu.adf.profe.Constantes

class DescargaReceiver : BroadcastReceiver() {

    var idDescarga: Long = -1


    override fun onReceive(context: Context, intent: Intent) {
        Log.d(Constantes.ETIQUETA_LOG, "Descarga Finalizada")

        //preparo mi consulta sobre el CONTENt PROVIDER DEL DONWLOADMANAGER
        val consulta = DownloadManager.Query()
        consulta.setFilterById(idDescarga)

        //obtengo el downloadmanager
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val cursor = downloadManager.query(consulta)

        cursor.use {
            if (cursor.moveToFirst())
            {
                val numColStatus = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val status = cursor.getInt(numColStatus)
                Log.d(Constantes.ETIQUETA_LOG, "Status descarga = $status")
            }
        }

        context.unregisterReceiver(this)


    }
}