package edu.adf.profe.descargarcanciones

import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.Constantes
import edu.adf.profe.R

class DescargarCancionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_descargar_cancion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun descargarCancion(view: View) {
        Log.d(Constantes.ETIQUETA_LOG, "DECARGANDO CANCIONES ... ")
        //PARTE 1 PREPARO DESCARGA
        //ESPACIO_TIEMPO
        var urlCancion = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview122/v4/1e/5d/4c/1e5d4c43-b1e5-ab2a-92dd-74317c1f4d0d/mzaf_6717404211778358689.plus.aac.p.m4a"
        var peticion = preprarDescarga(urlCancion)

        //programo el receptor para que reciba la señal de descarga completa
        var descargaReceiver = DescargaReceiver()
        var intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        //ASOCIO A MI RECEPTOR LA SEÑAL DE DESCARGA COMPLETA - "le pongo el intentfilter al receptor- programo el listener que escucha la descarga completada"
        ContextCompat.registerReceiver(this, descargaReceiver, intentFilter, ContextCompat.RECEIVER_EXPORTED)
        //PARTE 2 SOLICITO DESCARGA
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val idDescarga = downloadManager.enqueue(peticion)//me pongo a la cola y me da un número
        descargaReceiver.idDescarga = idDescarga //me guardo el id de la descarga para luego comprobar si ha ido bien
        descargaReceiver.activity = this

    }
    //

    private fun preprarDescarga(urlCancion: String): DownloadManager.Request {

        var peticion: DownloadManager.Request

            peticion = DownloadManager.Request(urlCancion.toUri()) //qué descargo
            peticion.setMimeType("audio/mp3")  //qué tipo mime
            peticion.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            peticion.setTitle("canción")
            peticion.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "cancionja.mp3")

        return peticion

    }

    fun actualizarEstadoDescarga (estadoDescarga: Int, rutaLocalCancion:String)
    {
        if (estadoDescarga == DownloadManager.STATUS_FAILED)
        {
            Log.d(Constantes.ETIQUETA_LOG, "La descarga fue mal")

        } else {
            Log.d(Constantes.ETIQUETA_LOG, "La descarga fue bien")
            //mostrar el botón del play
            val botonPLay =  findViewById<ImageButton>(R.id.botonplay)
            botonPLay.visibility = View.VISIBLE
            botonPLay.setOnClickListener {
                val mp = MediaPlayer()
                //mp.setDataSource(rutaLocalCancion)
                mp.setDataSource(this, rutaLocalCancion.toUri())
                mp.prepare()
                mp.start()
            }
        }
    }


}