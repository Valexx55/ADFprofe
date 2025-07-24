package edu.adf.profe

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
        supportActionBar?.hide()//oculto la appbar
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
}