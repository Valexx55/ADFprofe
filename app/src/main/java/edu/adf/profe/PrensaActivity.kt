package edu.adf.profe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.databinding.ActivityPrensaBinding

class PrensaActivity : AppCompatActivity() {

    val web1: String = "https://as.com/"
    val web2: String = "https://www.marca.com/"
    val web3: String = "https://www.mundodeportivo.com/"
    val web4: String = "https://www.sport.es/es/"

    lateinit var binding: ActivityPrensaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrensaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView1.settings.javaScriptEnabled = true
        binding.webView2.settings.javaScriptEnabled = true
        binding.webView3.settings.javaScriptEnabled = true
        binding.webView4.settings.javaScriptEnabled = true

        binding.webView1.loadUrl(web1)
        binding.webView2.loadUrl(web2)
        binding.webView3.loadUrl(web3)
        binding.webView4.loadUrl(web4)


    }
}