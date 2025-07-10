package edu.adf.profe


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random


/**
 * Esta actividad, muestra un juego para que el usuario adivine un número
 * Al inicio, el programa, piensa un número entre 1 y 100, que será sercreto
 * y es el que debe adivinar el usuario
 *
 * El usuario dispone de 5 intentos para adivinar el número secreto.
 * Por cada intento, si falla, el programa le debe informar de si el número buscado
 * es menor o mayor que el número introducido
 *
 * Se va restando las vidas, y si se consumen los 5 intentos sin acertar, le mostramos
 * al usuario un mensaje de derrota y el número buscado
 *
 * Si el usuario acierta, le mostramos un mensaje de ENHORABUENA
 */
class AdivinaNumeroActivity : AppCompatActivity() {

    var numeroSecreto:Int = 0
    var numeroVidas: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adivina_numero)
        numeroSecreto = generarNumeroSecreto()

    }

    fun intentoAdivina(view: View) {
        Log.d ("MIAPP", "El usuario ha dado a probar")
        //TODO continuar con la APP
    }

    fun generarNumeroSecreto(): Int
    {
        var numeroSecreto: Int = 0

            numeroSecreto = Random.nextInt(1,100)

        return numeroSecreto
    }
}