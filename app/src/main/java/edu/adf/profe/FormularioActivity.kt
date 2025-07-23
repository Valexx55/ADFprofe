package edu.adf.profe

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.content.SharedPreferences
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

    //TODO al inicio de la actividad, comprobad si hay datos guardados del usuario en el fichero
    //de preferencias y si los hay, iniciar la actividad, rellenando con los datos del fichero
    //el formulario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //he ocultado la barra desde el tema del manifest específico para esta actividad
       //TODO validación / snackBar

        //si hay datos en el fichero
        val ficheroUsuario = getSharedPreferences("usuario", MODE_PRIVATE)
        if (ficheroUsuario.all.isNotEmpty())
        {
            Log.d("MIAPP", "NO ESTÁ VACÍO, el fichero tiene datos de un usuario")
            //leo el fichero y relleno el formulario
            cargarFormularioConDatosFichero(ficheroUsuario)
        }
        else {
            Log.d("MIAPP", "El fichero de preferencias está vacío")
            //si no, pues no hago nada
        }




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

    fun cargarFormularioConDatosFichero (fichero: SharedPreferences)
    {
        val nombre = fichero.getString("nombre", "")//leo del fichero
        binding.editTextNombreFormulario.setText(nombre)//lo mento en la caja del nombre

        val edad = fichero.getInt("edad", 0)//leo la edad
        binding.editTextEdadFormulario.setText(edad.toString())//la meto en la caja

        val color = fichero.getInt("color", 0)//leo el color
        binding.colorFavorito.setBackgroundColor(color)//pongo el color del fondo en el botón

        val sexo = fichero.getString("sexo", "M")
        if (sexo=="M")
        {
            binding.radioButtonHombre.isChecked = true
        } else {
            binding.radioButtonMujer.isChecked = true
        }

        val mayorEdad = fichero.getBoolean("mayorEdad", false)
        binding.checkBox.isChecked = mayorEdad

       // binding.checkBox.isChecked = fichero.getBoolean("mayorEdad", false)
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



       /* binding.editTextEdadFormulario.setOnFocusChangeListener { v: View?, hasFocus: Boolean ->

        }*/

        val mayorEdad: Boolean = binding.checkBox.isChecked
        val usuario: Usuario = Usuario(nombre, edad, sexo, mayorEdad, this.color)
        //val usuario: Usuario = Usuario(nombre, edad, sexo, mayorEdad)
        Log.d("MIAPP", "USUARIO = $usuario" )
        guardarUsuario(usuario)
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

    fun guardarUsuario (usuario: Usuario)
    {
        //1 accedo al fichero (se crea automáticamente si no existe)
        val ficheroUsuario = getSharedPreferences("usuario", MODE_PRIVATE)

        val editor = ficheroUsuario.edit()//obtengo un editor para poder escribir a través de él en el fichero
        editor.putString("nombre", usuario.nombre)
        editor.putInt("edad", usuario.edad)
        editor.putString ("sexo", usuario.sexo.toString())
        editor.putInt ("color", usuario.colorFavorito)
        editor.putBoolean("mayorEdad", usuario.esMayorEdad)
        editor.apply()//o commit - guardo los cambios de verdad en el ficheros- se confirman, se hacen efectivos

    }

}