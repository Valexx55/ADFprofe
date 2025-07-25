package edu.adf.profe


import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import com.bumptech.glide.Glide


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
    var numeroVidas: Int = Constantes.NUM_VIDAS_JUEGO_ADIVINA
    var haGanado: Boolean = false //variables miembro/"globales"
    var haPerdido: Boolean =false
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

        this.haGanado = saquito?.getBoolean("haGanado") ?: false
        this.haPerdido = saquito?.getBoolean("haPerdido") ?: false

        if (this.haGanado)
        {
            actualizarImagen(R.drawable.imagen_victoria)
        } else if (this.haPerdido)
        {
            actualizarImagen(R.drawable.imagen_derrota)
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
        saquito.putBoolean("haPerdido", this.haPerdido)
        saquito.putBoolean("haGanado", this.haGanado)



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
        Log.d (Constantes.ETIQUETA_LOG, "El usuario ha dado a probar")
        //TODO continuar con la APP
        //val cajaNumUsuario = findViewById<EditText>(R.id.numeroUsuario)
        val numeroUsuario = this.cajaNumeroUsuario.text.toString().toInt()
        when {
            numeroUsuario > numeroSecreto -> {
                informarMenor()
                this.cajaNumeroUsuario.setText("")
            }
            numeroUsuario < numeroSecreto -> {
                informarMayor ()
                this.cajaNumeroUsuario.setText("")
            }
            else -> {
                findViewById<ImageButton>(R.id.botonReinicio).visibility = View.VISIBLE
                ganador()
                //
                haGanado = true
            }
        }
        if (!haGanado) //haGando == false
        {
            Log.d (Constantes.ETIQUETA_LOG, "El usuario no ha acertado")
            this.numeroVidas = this.numeroVidas-1
            findViewById<TextView>(R.id.numVidas).text = "$numeroVidas VIDAS"
            if (this.numeroVidas==0)
            {
                findViewById<ImageButton>(R.id.botonReinicio).visibility = View.VISIBLE
                informarGameOver()
                //findViewById<Button>(R.id.botonReinicio).visibility = View.VISIBLE
            }
        }

    }

    fun informarGameOver()
    {
        this.haPerdido = true
        val cajaMensajeFinal =  findViewById<TextView>(R.id.textoFinal)
        cajaMensajeFinal.text = "HAS PERDIDO, EL NÚMERO BUSCADO ERA $numeroSecreto"
        cajaMensajeFinal.visibility = View.VISIBLE
        findViewById<Button>(R.id.botonJugar).isEnabled = false
        actualizarImagen(R.drawable.imagen_derrota)
    }

    fun ganador ()
    {
        val cajaMensajeFinal =  findViewById<TextView>(R.id.textoFinal)
        cajaMensajeFinal.text = "HAS ACERTADO, ENHORABUENA"
        cajaMensajeFinal.visibility = View.VISIBLE
        findViewById<Button>(R.id.botonJugar).isEnabled = false
        actualizarImagen(R.drawable.imagen_victoria)
    }

    fun actualizarImagen (imagen: Int)
    {
       // findViewById<ImageView>(R.id.imagenAdivina).setImageResource(imagen)
       /* findViewById<ImageView>(R.id.imagenAdivina).load(imagen) {
            crossfade(true)
        }*/ //no funciona con COIL

        Glide.with(this)
            .asGif()
            .load(imagen) // puede ser un recurso o una URL
            .into(findViewById<ImageView>(R.id.imagenAdivina))

    }

    fun generarNumeroSecreto(): Int
    {
        var numeroSecretoLocal: Int = 0

            numeroSecretoLocal = Random.nextInt(1,100)

        return numeroSecretoLocal
    }

    fun reiniciarPartida(view: View) {
        //recreate()//esto reinicia la pantalla, pero llama a onSaveInstanceState y en este caso, no me interesa, porque la partida ha terminado y no quiero guardar nada
        finish()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            val options = ActivityOptions.makeCustomAnimation(this, 0, 0)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)// creo la pantalla otra vez
            overridePendingTransition(0, 0)
        }



    }
}