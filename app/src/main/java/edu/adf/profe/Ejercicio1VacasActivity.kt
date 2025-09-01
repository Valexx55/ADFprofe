package edu.adf.profe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import edu.adf.profe.databinding.ActivityEjercicio1VacasBinding
import edu.adf.profe.databinding.ActivityVideoBinding
import java.util.ArrayList

class Ejercicio1VacasActivity : AppCompatActivity() {

    var ncambios: Int = 0
    lateinit var binding:ActivityEjercicio1VacasBinding

    var arrayIdsTocados = arrayListOf<Int>()  // intArrayOf(4) // mutableListOf<Int>()//creo un array de números vacío

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio1VacasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_ejercicio1_vacas)
        if (savedInstanceState!=null)//si hay ids en el array, es que hay cajas ya tocadas
        {
            val arrayInts = savedInstanceState.getIntArray("arrayIdsTocados")!!
            arrayInts.iterator().forEach { this.arrayIdsTocados.add(it) } //paso de array de kotlin a array de java (el array de java me permite crecer dinámicamnete sin complicaciones)
            this.arrayIdsTocados.iterator().forEach {  //recorremos las vistas
                var vista = findViewById<LinearLayout>(it) //y las vistas aquí guardadas han sido tocadas
                vista.setBackgroundColor(ContextCompat.getColor(this, R.color.black)) //y las pintamos de negro (tocadas)

            }
            this.ncambios = this.arrayIdsTocados.size //para controlar el final del juego, hemos contabilizado el número de vistas y lo actualizamos


        }

    }

    fun cambiarFondo(view: View) {
        val valor = view.tag
        Log.d(Constantes.ETIQUETA_LOG, "VISTA ID = ${view.id}")
        if (valor==null)//no tengo nada asociado, es la primera vez que toco es caja/vista
        {
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.black)) //y las pintamos de negro (tocadas)
            view.tag=true
            arrayIdsTocados.add(view.id)//añado el id de la vista tocada para guardarla
            ncambios = ncambios+1//actualizamos el número de cajas tocadas
            if (ncambios==4)
            {
                Snackbar.make(binding.main, "JUEGO TERMINADO", BaseTransientBottomBar.LENGTH_LONG)
                    .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int)
                    {
                        super.onDismissed(transientBottomBar, event)
                        finish() //hacemos el finish de la actividad después de quitarse el snackbar
                    }
                }).show()

            }


        }
        //si ya está en negro
            //no hago nada
        //si no (si está en azul)
            //lo paso a negro

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val intArray: IntArray = arrayIdsTocados.map { it.toInt() }.toIntArray()//no podemos guardar un array de Java en el bundle, así que transformamos el array de java a Kotlin, para poder hacerlo

        outState.putIntArray("arrayIdsTocados", intArray)

    }




}