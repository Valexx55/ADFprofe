package edu.adf.profe

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImagenResultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val resultado = intent.getStringExtra("resultado")//"Leyendo del saco"
        Log.d("MIAPP", "RESULTADO = $resultado")
        //resultado?.length;
        setContentView(R.layout.activity_imagen_resultado)
        cambiarTextoEImagen(resultado!!)
    }

    fun cambiarTextoEImagen(resultado:String):Unit
    {
        val imageView:ImageView = findViewById<ImageView>(R.id.fotoResultado)

        var imagenImc:Int = when (resultado) {
            "DESNUTRIDO" -> R.drawable.imc_desnutrido
            "DELGADO" -> R.drawable.imc_delgado
            "IDEAL" -> R.drawable.imc_ideal
            "SOBREPESO" -> R.drawable.imc_sobrepeso
            "OBESO" ->  R.drawable.imc_obeso
            else -> R.drawable.imc_desnutrido
        }
        imageView.setImageResource(imagenImc)

        val tvLeyenda: TextView = findViewById<TextView>(R.id.leyenda)
        tvLeyenda.text = resultado
    }
}