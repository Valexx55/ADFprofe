package edu.adf.profe

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FormularioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_formulario)
        //TODO ocultar el APPBar programáticamente
        //TODO formulario dinámico / ANIMADO
    }

    fun seleccionarColorFavorito(view: View) {}
}