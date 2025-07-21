package edu.adf.profe

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import edu.adf.profe.databinding.ActivitySpinnerBinding

class SpinnerActivity : AppCompatActivity() {

   lateinit var binding: ActivitySpinnerBinding
   val arrayVisibilidad = arrayOf("VISIBLE", "INVISIBLE", "GONE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //INICIAMOS EL SPINNER - ADAPTER -proveedor
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayVisibilidad) //estilo lista plegada
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) //estilo lista desplegada
        binding.spinner.adapter = spinnerAdapter


    }
}