package edu.adf.profe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    val webAdf: String = "https://adf-formacion.es/"
    val rutaLocalWebAdf: String = "file:///android_asset/adf.html"
    lateinit var binding: ActivityWebViewBinding // clase intermedia que se genera automáticamente y me da acceso a las vistas sin findviewbyid

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //activo JavaScript
        binding.webView.settings.javaScriptEnabled = true
        //si cargo una página sin el permiso de internet en la app, fallará
        //binding.webView.loadUrl(webAdf)//con esto cargamos la página web en nuestra webview
        binding.webView.loadUrl(rutaLocalWebAdf)

    }
}