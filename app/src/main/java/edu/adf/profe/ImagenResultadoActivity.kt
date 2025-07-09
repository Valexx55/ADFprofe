package edu.adf.profe

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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

    fun cambiarTextoEImagen (resultado:String)
    {
        //TODO completar esta función para que muestre una imagen y un texto adecuados al string resultado recibido
        val imageView:ImageView = findViewById<ImageView>(R.id.fotoResultado)
        imageView.setImageResource(R.drawable.imc_obeso)
    }
}