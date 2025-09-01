package edu.adf.profe

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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
    //Build.VERSION.RELEASE - NÚMERO DE VERSION DE ANDROID (PARA USUARIOS)
    //Build.VERSION.SDK_INT - VERSIÓN DEL API (MÁS TÉCNICO)
    //Build.VERSION.CODENAME - NOMBRE CLAVE/ "DE GUERRA"

    val infoVersion =  " (${Build.VERSION.RELEASE})\nAPI ${Build.VERSION.SDK_INT} "

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Constantes.ETIQUETA_LOG, "Entrando en oncreate ()")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d(Constantes.ETIQUETA_LOG, "Saliendo de oncreate ()")
    }

    fun saludoTocado(view: View) {
        findViewById<TextView>(R.id.mensajeSalida).text = "HOLA MUNDO"
    }
    fun informarVersion(view: View) {

        findViewById<TextView>(R.id.mensajeSalida).text = getAndroidCodename(Build.VERSION.SDK_INT) + infoVersion

        /**
         * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         *     // Código solo para Android 13 o superior
         * } else {
         *     // Código para versiones anteriores
         * }
         *
         */

    }


    fun getAndroidCodename(sdkInt: Int): String {
        return when (sdkInt) {
            36 -> "Baklava"            // Android 16
            35 -> "VanillaIceCream"    // Android 15
            34 -> "Upside Down Cake"   // Android 14
            33 -> "Tiramisu"           // Android 13
            32 -> "Android 12L"        // Android 12L
            31 -> "Snow Cone"          // Android 12
            30 -> "Red Velvet Cake"    // Android 11
            29 -> "Quince Tart"        // Android 10
            28 -> "Pie"                // Android 9
            27 -> "Oreo"               // Android 8.1
            26 -> "Oreo"               // Android 8.0
            25 -> "Nougat"             // Android 7.1
            24 -> "Nougat"             // Android 7.0
            23 -> "Marshmallow"
            22 -> "Lollipop"
            21 -> "Lollipop"
            20 -> "KitKat Watch"
            19 -> "KitKat"
            18 -> "Jelly Bean"
            17 -> "Jelly Bean"
            16 -> "Jelly Bean"
            15 -> "Ice Cream Sandwich"
            14 -> "Ice Cream Sandwich"
            13 -> "Honeycomb"
            12 -> "Honeycomb"
            11 -> "Honeycomb"
            10 -> "Gingerbread"
            9 -> "Gingerbread"
            8 -> "FroYo"
            7 -> "Eclair"
            6 -> "Eclair"
            5 -> "Eclair"
            4 -> "Donut"
            3 -> "Cupcake"
            2 -> "Banana Bread"
            1 -> "Apple Pie"
            else -> "Unknown"
        }
    }

}