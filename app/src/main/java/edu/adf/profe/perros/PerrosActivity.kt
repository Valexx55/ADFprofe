package edu.adf.profe.perros

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityPerrosBinding

class PerrosActivity : AppCompatActivity() {

    lateinit var binding: ActivityPerrosBinding
    var razaSeleccionada = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()
    }

    private fun initActivity() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.razas,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) //estilo lista desplegada

        binding.spinnerRazas.adapter = adapter


        binding.spinnerRazas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                opcionTocada: View?,
                position: Int,
                id: Long
            ) {

                    razaSeleccionada = (opcionTocada as TextView).text.toString()
                    Log.d(Constantes.ETIQUETA_LOG, "RAZA seleccionada =  $razaSeleccionada")


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(Constantes.ETIQUETA_LOG, "OPCIÓN ELIMINADA DEL SPINNER")
            }
        }
    }

    fun buscarFotos(view: View) {
        Log.d(Constantes.ETIQUETA_LOG, "A buscar Fotos de  =  $razaSeleccionada")
        //TODO
        /*
        1 TRANSITO A GALERIA PERROS ACTIVITY PASANDO EN EL INTENT LA RAZA
        2 OBTENGO EL LISTADO DE FOTOS DE ESA RAZA LLAMANDO A https://dog.ceo/api/breed/{RAZA}/images
         */


    }
}