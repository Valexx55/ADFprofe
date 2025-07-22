package edu.adf.profe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import edu.adf.profe.databinding.ActivitySpinnerBinding

class SpinnerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

   lateinit var binding: ActivitySpinnerBinding
   val arrayVisibilidad = arrayOf("VISIBLE", "INVISIBLE", "GONE")
   var primeraVez: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //INICIAMOS EL SPINNER - ADAPTER -proveedor
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayVisibilidad) //estilo lista plegada
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) //estilo lista desplegada
        binding.spinner.adapter = spinnerAdapter

        //CASO ESPECIAL PORQUE AdapterView.OnItemSelectedListener TIENE 2 MÉTODOS ABSTRACTOS
        //SI SÓLO TUVIERA UNO (ONLCICK) PODRÍAMOS HABERLO IMPLEMENTADO CON UNA FUNCIÓN LAMBDA/ANÓNIMA
        //binding.spinner.onItemSelectedListener = this//otra alternativa: que la clase implemente la interfaz
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (primeraVez)
                {
                    Log.d("MIAPP", "ES LA PRIMERA VEZ (el usuario en realidad no ha tocado el spinner)")
                    primeraVez = false
                } else {

                    Log.d("MIAPP", "OPCIÓN $position SELECCIONADA")
                    //si toca el cero --> visible - la imagen la dejo visible
                    //si toca el uno --> invisible - la imagen la dejo en invisible
                    //si toca el dos --> gone -- la imagen la dejo en gone
                    when (position)
                    {
                        0-> binding.imagenMuestra.visibility = View.VISIBLE
                        1-> binding.imagenMuestra.visibility = View.INVISIBLE
                        2-> binding.imagenMuestra.visibility = View.GONE
                    }

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("MIAPP", "OPCIÓN ELIMINADA DEL SPINNER")
            }
        }


    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        if (primeraVez)
        {
            Log.d("MIAPP", "ES LA PRIMERA VEZ (el usuario en realidad no ha tocado el spinner)")
            primeraVez = false
        } else {

            Log.d("MIAPP", "OPCIÓN $position SELECCIONADA")
            //si toca el cero --> visible - la imagen la dejo visible
            //si toca el uno --> invisible - la imagen la dejo en invisible
            //si toca el dos --> gone -- la imagen la dejo en gone
            when (position)
            {
                0-> binding.imagenMuestra.visibility = View.VISIBLE
                1-> binding.imagenMuestra.visibility = View.INVISIBLE
                2-> binding.imagenMuestra.visibility = View.GONE
            }

        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("MIAPP", "OPCIÓN ELIMINADA DEL SPINNER")
    }
}