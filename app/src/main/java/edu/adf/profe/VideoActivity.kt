package edu.adf.profe

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import edu.adf.profe.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //enableEdgeToEdge()
        supportActionBar?.hide()//oculto la appbar
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN //forma antigua  - deprecated
        ocultarBarraEstado()

        //var rutaUriVideo = "android.resource://$packageName/"+R.raw.video_inicio
        var rutaUriVideo = "android.resource://$packageName/${R.raw.video_inicio}"
        Log.d(Constantes.ETIQUETA_LOG, "RUTA VIDEO = $rutaUriVideo")
        binding.videoView.setVideoURI(rutaUriVideo.toUri())

        binding.videoView.start()//reproduzco el vídeo


    }

    fun ocultarBarraEstado() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun saltarPresentacion(view: View) {
        finish()//vuelvo al menú

    }
    fun noVolverAMostrar(view: View) {
        val fichero = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS_INICIO, MODE_PRIVATE)
        fichero.edit (true){
            putBoolean("SALTAR_VIDEO", true)
        }

    }
}