package edu.adf.profe

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.databinding.ActivityFormularioBinding

//TODO hacer que el Usuario pueda seleccionar una FOTo y que se visualice en el IMAGEVIEW
class FormularioActivity : AppCompatActivity() {

    //para lanzar una subactividad (un actividad que me da un resultado)
    lateinit var lanzador: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityFormularioBinding
    var color: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //he ocultado la barra desde el tema del manifest específico para esta actividad
       //TODO validación / snackBar

        lanzador = registerForActivityResult (
            ActivityResultContracts.StartActivityForResult() //lo que lanzo es una actividad
        ){
            //la función que recibe el resultado
            result ->
            if (result.resultCode == Activity.RESULT_OK)
            {
                Log.d("MIAPP", "La subactividad ha FINALIZADO BIEN ${result.resultCode}")
                val intent_resultado = result.data
                color = intent_resultado?.getIntExtra("COLOR_ELEGIDO", 0) ?: 0
                binding.colorFavorito.setBackgroundColor(color)
            } else {
                Log.d("MIAPP", "La subactividad ha FINALIZADO MAL ${result.resultCode}")
            }

        }
    }

    fun seleccionarColorFavorito(view: View) {
        //DEBEMOS LANZAR LA OTRA ACTIVIDAD SUBCOLOR ACTIVITY, PERO COMO SUBACTIVIDAD
        val intent = Intent(this, SubColorActivity::class.java)
        //startActivity(intent)
        //startActivityForResult(intent, 99)
        lanzador.launch(intent)//aquí lanzo la subactividad
    }

    fun mostrarInfoFormulario(view: View) {
        //mostrar los datos del formulario
        Log.d("MIAPP", "NOMBRE = ${binding.editTextNombreFormulario.text.toString()} EDAD = ${binding.editTextEdadFormulario.text.toString()} HOMBRE = ${binding.radioButtonHombre.isChecked} MUJER = ${binding.radioButtonMujer.isChecked} MAYOR EDAD = ${binding.checkBox.isChecked}" )
        //TODO crear una clase Usuario, para albergar toda la información obtenidad en el formulario
        val nombre:String = binding.editTextNombreFormulario.text.toString()
        val edad: Int = binding.editTextEdadFormulario.text.toString().toInt()
        val sexo: Char = if (binding.radioButtonHombre.isChecked)
        {
            'M'
        } else if (binding.radioButtonMujer.isChecked) {
            'F'
        } else {
            'M'
        }

        val mayorEdad: Boolean = binding.checkBox.isChecked
        val usuario: Usuario = Usuario(nombre, edad, sexo, mayorEdad, this.color)
        //val usuario: Usuario = Usuario(nombre, edad, sexo, mayorEdad)
        Log.d("MIAPP", "USUARIO = $usuario" )

    }

    /**
     *
     versión antigua
    */
    /*
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        //obtenía el resultado
    }

     */


}