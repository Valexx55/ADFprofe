package edu.adf.profe

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SubColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_color)
        //ocultar el APPBar programáticamente
        supportActionBar?.hide()
        //finish()
    }

    //view es la Vista tocada
    fun colorSeleccionado(view: View) {
        val fondo = view.background
        val colorfondo = fondo as ColorDrawable//casting
        val color = colorfondo.color
        Log.d(Constantes.ETIQUETA_LOG, "COLOR SELECCIONADO = $color")

        //guardar el color seleccioando como la resultado de la actividad
        //y finalizar la actividad
        val intent_resutaldo = Intent() //este intent, representa el valor devuelto por la actividad  memoria temporal
        intent_resutaldo.putExtra("COLOR_ELEGIDO", color)

        setResult(RESULT_OK, intent_resutaldo)
        finish()


    }
}