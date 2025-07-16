package edu.adf.profe

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SumaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_suma)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun sumar(view: View) {
        //coger el primer número
        var numero1:Int = findViewById<EditText>(R.id.cajaUno).text.toString().toInt()
        //coger el segundo número
        var numero2:Int = findViewById<EditText>(R.id.cajaDos).text.toString().toInt()
        //sumar los dos números
        var suma = numero1+numero2
        //mostrar resultado
        findViewById<TextView>(R.id.resultadoSuma).text = suma.toString()
    }
}