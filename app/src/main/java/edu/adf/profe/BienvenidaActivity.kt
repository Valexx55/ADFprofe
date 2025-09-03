package edu.adf.profe

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BienvenidaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvb = findViewById<TextView>(R.id.textoBienvenida)
        var usuario:Usuario?
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU)
        {
            usuario = intent.getParcelableExtra("USUARIO", Usuario::class.java)
        } else {
            usuario = intent.getParcelableExtra("USUARIO")
        }
        tvb.text = "BIENVENIDO \n ${usuario?.nombre} ${usuario?.edad} "
        tvb.setTextColor(usuario!!.colorFavorito)


    }
}