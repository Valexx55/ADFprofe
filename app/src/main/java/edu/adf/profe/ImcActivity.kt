package edu.adf.profe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImcActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun calcularImc(view: View) {
        //LISTENER / CALLBACK
        //TODO hacer el cálculo del IMC
        Log.d("MIAPP", "El usuario ha tocado el botón de calcular IMC")
        //creamos la notificación/mensaje
        val toast:Toast = Toast.makeText(this, "El usuario ha tocado el botón de calcular IMC", Toast.LENGTH_LONG)
        //mostrar la notificación
        toast.show()
        //cerrar la activity (si hubiera una ventana/activity apilada -detrás-, se mostraría. Si no, te sales de la app)
        finish()
        //salir de la app / cerrar todas las ventanas
        finishAffinity()

    }

    /**
     *
     * public static Toast makeText(
     *     android.content.Context context,
     *     CharSequence text,
     *     int duration
     * )
     */
}