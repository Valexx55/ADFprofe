package edu.adf.profe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class InflarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inflar)
        mostrarLayout(findViewById<View>(R.id.principal_layout))
    }

    fun mensajeSalida(nombre: String): String {
        var mensaje: String? = null

             mensaje = "El nombre tiene " + nombre.length + " letras"

        return mensaje
    }

    fun mostrarMensajeSalida(view: View) {
        val editText = findViewById<EditText>(R.id.editText)
        val nombre = editText.text.toString()

        Log.d(javaClass.canonicalName, "Ha introducido el nombre = $nombre")

        val mensaje: String = mensajeSalida(nombre)


        val caja_resultado = findViewById<LinearLayout>(R.id.resultado)

        if (caja_resultado.childCount > 0)  //la lista ya ha sido inflada
        {
            val text = findViewById<TextView>(R.id.mensaje_salida)
            text.text = mensaje
        } else {
            val layoutInflater = layoutInflater //o LayoutInflater.from(a)
            val vista_inflada: View =
                layoutInflater.inflate(R.layout.mensaje_salida, caja_resultado)
            val text = vista_inflada.findViewById<TextView>(R.id.mensaje_salida)
            text.text = mensaje
        }


        mostrarLayout(findViewById<View>(R.id.principal_layout))
    }



    private fun mostrarLayout(vista: View) {
        Log.d(Constantes.ETIQUETA_LOG, vista.javaClass.canonicalName)

        if (vista is ViewGroup) {
            val viewGroup = vista

            for (i in 0..<viewGroup.childCount) {
                val vistahija = viewGroup.getChildAt(i)
                mostrarLayout(vistahija)
            }
        }
    }

}