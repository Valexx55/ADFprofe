package edu.adf.profe

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * esto es un comentario multilínea
 *
 * Esta es una Activity -- PANTALLA
 *
 *
 * ESTO ES LA PARTE FUNCIONAL, LA PARTE DE PROGRAMACIÓN
 * EN EL XML, TENEMOS LA PARTE VISUAL
 */
class VersionActivity : AppCompatActivity() // esto es un comentario
{

    //ATRIBUTOS / campos
    //MÉTODOS / FUNCIONES

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MIAPP", "Entrando en oncreate ()")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("MIAPP", "Saliendo de oncreate ()")
    }
}