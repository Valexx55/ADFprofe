package edu.adf.profe


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
    var haGanado: Boolean = false //variables miembro/"globales"
    lateinit var cajaNumeroUsuario: EditText

    //TODO MODIFICAR LA APP, PARA QUE AL DAR LA VUELTA EL MÓVIL, NO SE PIERDAN
    //NI EL NÚMERO DE VIDAS, NI EL NÚMERO SECRETO

    override fun onCreate(saquito: Bundle?) {
        super.onCreate(saquito)
        setContentView(R.layout.activity_adivina_numero) // hasta que no se ejecuta esta instrucción, no puedo referirme a ninguna
        this.cajaNumeroUsuario = findViewById<EditText>(R.id.numeroUsuario)
        this.numeroSecreto =  saquito?.getInt("numerosecreto") ?:  generarNumeroSecreto()
        this.numeroVidas =  saquito?.getInt("numvidas") ?: 5
        findViewById<TextView>(R.id.numVidas).text= "$numeroVidas VIDAS"
        if (this.numeroVidas==0)
        {
            findViewById<Button>(R.id.botonJugar).isEnabled = false
        }
        var textoFinal = saquito?.getString("textoFinal") ?: ""
        if (textoFinal.isNotEmpty())
        {
            findViewById<TextView>(R.id.textoFinal).text = textoFinal
            findViewById<TextView>(R.id.textoFinal).visibility = View.VISIBLE
        }


    }

    override fun onSaveInstanceState(saquito: Bundle) {
        super.onSaveInstanceState(saquito)
        saquito.putInt("numerosecreto", this.numeroSecreto)
        saquito.putInt("numvidas", this.numeroVidas)
        var textoFinal =  findViewById<TextView>(R.id.textoFinal).text.toString()
        if (textoFinal!="null" && textoFinal.isNotEmpty())
        {
            saquito.putString("textoFinal", textoFinal)
        }


    }

    fun informarMenor ()
    {
        Toast.makeText(this, "El número buscado es menor", Toast.LENGTH_LONG).show()
    }

    fun informarMayor ()
    {
        Toast.makeText(this, "El número buscado es mayor", Toast.LENGTH_LONG).show()
    }

    fun intentoAdivina(view: View) {
        Log.d ("MIAPP", "El usuario ha dado a probar")
        //TODO continuar con la APP
        //val cajaNumUsuario = findViewById<EditText>(R.id.numeroUsuario)
        val numeroUsuario = this.cajaNumeroUsuario.text.toString().toInt()
        when {
            numeroUsuario > numeroSecreto -> informarMenor()
            numeroUsuario < numeroSecreto -> informarMayor ()
            else -> {
                ganador()
                haGanado = true
            }
        }
        if (!haGanado) //haGando == false
        {
            Log.d ("MIAPP", "El usuario no ha acertado")
            this.numeroVidas = this.numeroVidas-1
            findViewById<TextView>(R.id.numVidas).text = "$numeroVidas VIDAS"
            if (this.numeroVidas==0)
            {
                informarGameOver()
            }
        }

    }

    fun informarGameOver()
    {
        val cajaMensajeFinal =  findViewById<TextView>(R.id.textoFinal)
        cajaMensajeFinal.text = "HAS PERDIDO, EL NÚMERO BUSCADO ERA $numeroSecreto"
        cajaMensajeFinal.visibility = View.VISIBLE
        findViewById<Button>(R.id.botonJugar).isEnabled = false

    }

    fun ganador ()
    {
        val cajaMensajeFinal =  findViewById<TextView>(R.id.textoFinal)
        cajaMensajeFinal.text = "HAS ACERTADO, ENHORABUENA"
        cajaMensajeFinal.visibility = View.VISIBLE
        findViewById<Button>(R.id.botonJugar).isEnabled = false
    }

    fun generarNumeroSecreto(): Int
    {
        var numeroSecretoLocal: Int = 0

            numeroSecretoLocal = Random.nextInt(1,100)

        return numeroSecretoLocal
    }
}