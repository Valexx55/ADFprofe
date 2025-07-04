package edu.adf.profe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * SI TU IMC ESTÁ POR DEBAJO DE 16 , TU IMC ES DESNUTRIDO
 * SI TU IMC ES MAYOR O IGUAL A 16 Y MENOR QUE 18 --> DELGADO
 * SI TU IMC ES MAYOR O IGUAL A 18 Y MENOR QUE 25 --> IDEAL
 * SI TU IMC ES MAYOR O IGUAL A 25 Y MENOR QUE 31 --> SOBREPESO
 * SI TU IMC ES MAYOR O IGUAL que 31 --> OBESO
 */

//TODO pasar del valo número al valor nominal 20 --> ESTÁS IDEAL O BIEN CON IF O BIEN CON WHEN


class ImcActivity : AppCompatActivity() {

    var numeroVecesBoton: Int = 0 //PARA LLEVAR LA CUENTA de veces que el usuario toca el botón

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

        Log.d("MIAPP", "El usuario ha tocado el botón de calcular IMC")
        informarNumVecesBoton()
        //IMC = peso / (altura al cuadrado)  DIVISIÓN --> / multiplicación *
        //1 obtener el peso introducido
        val peso:Float = obtenerPeso()
        Log.d("MIAPP", "PESO introducido $peso")
        //2 obtener la altura introducida
        val altura = obtenerAltura()
        Log.d("MIAPP", "ALTURA introducido $altura")
        //3 hacer la fórmula del imc - PARÁMETROS ACTUALES
        val resultadoImc : Float = calculoImcNumerico(peso, altura) //LLAMADA/INVOKE - debe haber una coincidencia entre el tipo, número y el orden de los parámetros/variables
        Log.d("MIAPP", "ALTURA introducido $resultadoImc")
        //4 mostrar el resultado
        mostrarResultado(resultadoImc)


    }

    fun mostrarResultado(resultadoImc: Float) //: Unit - void no devuelvo nada - no tengo return
    {
        val mensaje: Toast = Toast.makeText(this, "SU IMC ES $resultadoImc", Toast.LENGTH_LONG)
        mensaje.show()
    }

    //PARÁMETROS FORMALES
    fun calculoImcNumerico(peso:Float, altura:Float): Float //CABECERA
    {
        //1 declaro una variable del tipo devuelto
        var imc:Float = 0f

            imc = peso / (altura*altura)

        return imc
    }

    fun obtenerPeso (): Float
    {
        //uso var cuando la variable decalarada puede cambiar su valor después (mutable)
        var peso:Float = 0f //inicializo la variable del peso

            //tengo que coger el valor de la caja
            //obtener la caja
            //cuando tenga una variable/dato que no cambia su valor, uso val
            val editTextPeso: EditText = findViewById<EditText>(R.id.cajaPeso)
            //"33" text --> 33.0 -- float
            peso = editTextPeso.text.toString().toFloat() //accedo al contenido y lo traduzco a número


        return peso
    }


    fun obtenerAltura (): Float
    {
        //uso var cuando la variable decalarada puede cambiar su valor después (mutable)
        var altura:Float = 0f //inicializo la variable del peso

        //tengo que coger el valor de la caja
        //obtener la caja
        //cuando tenga una variable/dato que no cambia su valor, uso val
        val editTextAltura: EditText = findViewById<EditText>(R.id.cajaAltura)
        //"33" text --> 33.0 -- float
        altura = editTextAltura.text.toString().toFloat() //accedo al contenido y lo traduzco a número


        return altura
    }

    fun informarNumVecesBoton ()
    {
        numeroVecesBoton = numeroVecesBoton+1; // + string concatenar --> unir palabras // + números sumar
        val toast:Toast = Toast.makeText(this, "El usuario ha tocado el botón de calcular IMC $numeroVecesBoton veces ", Toast.LENGTH_LONG)
        //mostrar la notificación
        toast.show()
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