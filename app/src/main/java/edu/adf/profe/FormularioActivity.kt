package edu.adf.profe

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.net.toUri
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import edu.adf.profe.databinding.ActivityFormularioBinding
import java.io.File
import java.io.FileOutputStream


class FormularioActivity : AppCompatActivity() {

    //para lanzar una subactividad (un actividad que me da un resultado)
    lateinit var lanzadorColorFavorito: ActivityResultLauncher<Intent>
    lateinit var lanzadorImagenFormulario: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityFormularioBinding
    var color: Int = 0
    lateinit var usuario:Usuario
    //var usuario:Usuario? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //he ocultado la barra desde el tema del manifest específico para esta actividad

        //si hay datos en el fichero
        val ficheroUsuario = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS, MODE_PRIVATE)
        if (ficheroUsuario.all.isNotEmpty())
        {
            Log.d(Constantes.ETIQUETA_LOG, "NO ESTÁ VACÍO, el fichero tiene datos de un usuario")
            //leo el fichero y relleno el formulario
            this.usuario = cargarFormularioConDatosFichero(ficheroUsuario)
            //NUEVO Como existe un usuario, queremos mostrar en otra actividad un mensaje de bienvenida
            val intent = Intent(this, BienvenidaActivity::class.java)
            intent.putExtra("USUARIO", usuario)
            startActivity(intent)
        }
        else {
            Log.d(Constantes.ETIQUETA_LOG, "El fichero de preferencias está vacío")
            //si no, pues no hago nada
        }

        //cuando la caja de texto del nombre pierda el foco, se valide
        //y si el nombre no es correcto, me salga en rojo
        binding.editTextNombreFormulario.setOnFocusChangeListener (fun (view: View, tieneFoco: Boolean) {
            if (tieneFoco)//==true
            {
                Log.d(Constantes.ETIQUETA_LOG, "La caja de nombre tiene el foco")
            } else {
                Log.d(Constantes.ETIQUETA_LOG, "La caja de nombre ha perdido el foco")
                if (!esNombreValido(binding.editTextNombreFormulario.text.toString()))//esNombreValido==false
                {
                    binding.tilnombre.error = "Nombre incorrecto - longitud menor que 3"

                } else {

                    binding.tilnombre.isErrorEnabled = false//borro el rojo del error
                }
            }

        })

        /*
        binding.editTextNombreFormulario.setOnFocusChangeListener { view: View, tieneFoco: Boolean ->
            if (tieneFoco)
            {
                Log.d(Constantes.ETIQUETA_LOG, "La caja de nombre tiene el foco")
            } else {
                Log.d(Constantes.ETIQUETA_LOG, "La caja de nombre ha perdido el foco")
                if (esNombreValido(this.binding.editTextNombreFormulario.text.toString()))
                {
                    binding.tilnombre.error = "Nombre incorrecto"
                } else {
                    binding.tilnombre.isErrorEnabled = false
                }
            }
        }
*/




        lanzadorColorFavorito = registerForActivityResult (
            ActivityResultContracts.StartActivityForResult() //lo que lanzo es una actividad
        ){
            //la función que recibe el resultado
            result ->
            if (result.resultCode == Activity.RESULT_OK)
            {
                Log.d(Constantes.ETIQUETA_LOG, "La subactividad ha FINALIZADO BIEN ${result.resultCode}")
                val intent_resultado = result.data
                color = intent_resultado?.getIntExtra("COLOR_ELEGIDO", 0) ?: 0
                binding.colorFavorito.setBackgroundColor(color)
            } else {
                Log.d(Constantes.ETIQUETA_LOG, "La subactividad ha FINALIZADO MAL ${result.resultCode}")
            }

        }

        lanzadorImagenFormulario = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                resultado: ActivityResult ->
            Log.d(Constantes.ETIQUETA_LOG, "Volviendo de la Galeria")
            if (resultado.resultCode == RESULT_OK) {
                Log.d(Constantes.ETIQUETA_LOG, "Volviendo de la Galeria ok")
                Log.d(Constantes.ETIQUETA_LOG, "RUTA FOTO =  ${resultado.data?.data}")
                binding.imagenFormulario.setImageURI(resultado.data?.data)
                binding.imagenFormulario.scaleType = ImageView.ScaleType.CENTER_CROP
                //en el propio ImageView, guardo la uri de la foto, para luego poder guardarla desde la Imagen setTag/getTag


                val urilocal = copiarImagenALocal(resultado.data?.data!!)
                Log.d(Constantes.ETIQUETA_LOG, "RUTA FOTO LOCAL =  ${urilocal}")
                binding.imagenFormulario.tag = urilocal

                //PEDIMOS PERMISOS PERMANTENES PARA ACCEDER A ESA FOTO DE LA GALERÍA DESPIUÉS (EN OTRO PROCESO, AL ARRANCAR EL PROGRAMA OTRA VEZ)
                //ESTA SOLUCIÓN FALLA! EL Content Provider de DOcuimentos no nos concende acceso permanente
                //val takeFlags = resultado.data?.flags!! and Intent.FLAG_GRANT_READ_URI_PERMISSION
                //contentResolver.takePersistableUriPermission(resultado.data?.data!!, takeFlags)



            } else {
                Log.d(Constantes.ETIQUETA_LOG, "Volviendo de la Galeria MAL")
            }

        }




/*
        lanzadorImagenFormulario = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            fun (res: ActivityResult) {
            res
        })

        lanzadorImagenFormulario = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            this::aLaVueltaSeleccionFoto) //function referencia

        lanzadorImagenFormulario = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // void	onActivityResult(result:ActivityResult)
            //it representa activityresult
        }*/

        //0) programar el listener onclick sobre imageview
        //1) Lanzar un intent implícito para seleccionar la foto
        //2) tengo que preparar el objeto para lanar el 1) y recibir su respuesta
        //3) a la vuelta, coger la foto y ponerla en el imageView
        //función lambda / flecha
        /*this.binding.imagenFormulario.setOnClickListener { imagen ->
                seleccionarFoto()
        }*/
        this.binding.imagenCargarFoto.setOnClickListener{
            seleccionarFoto()
        }

        /*this.binding.imagenFormulario.setOnClickListener {
            seleccionarFoto()
        }
        //función anónima
        this.binding.imagenFormulario.setOnClickListener ( fun (v: View) {
            seleccionarFoto()
        }
        )

        this.binding.imagenFormulario.setOnClickListener (this::seleccionarFoto2)*/


    }//fin oncreate

    fun seleccionarFoto ()
    {
        //val intentGaleria = Intent(Intent.ACTION_PICK) //intent implicito para ir a la galeria
        val intentGaleria = Intent(Intent.ACTION_GET_CONTENT) //intent implicito para ir a la galeria
        intentGaleria.type = "image/*"

        if (intentGaleria.resolveActivity(packageManager)!=null)
        {
            Log.d(Constantes.ETIQUETA_LOG, "SÍ HAY una APP de GALERIA")


            lanzadorImagenFormulario.launch(intentGaleria)
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "NO HAY APP de GALERÍA")
        }


    }
    fun seleccionarFoto2 (v: View)
    {

    }


    fun aLaVueltaSeleccionFoto (resultado: ActivityResult): Unit
    {

    }


    fun aLaVueltaSeleccionFoto2 (resultado: Intent): Unit
    {

    }

    fun cargarFormularioConDatosFichero (fichero: SharedPreferences):Usuario
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

        val uriFoto = fichero.getString("uriFoto", "")
        if (uriFoto!="")
        {
            binding.imagenFormulario.setImageURI(uriFoto?.toUri())
            binding.imagenFormulario.scaleType = ImageView.ScaleType.CENTER_CROP
        }



        val usuarioFichero = Usuario(nombre!!, edad, sexo!!.get(0), mayorEdad, color, uriFoto!!)

       // binding.checkBox.isChecked = fichero.getBoolean("mayorEdad", false)
        return usuarioFichero
    }

    fun seleccionarColorFavorito(view: View) {
        //DEBEMOS LANZAR LA OTRA ACTIVIDAD SUBCOLOR ACTIVITY, PERO COMO SUBACTIVIDAD
        val intent = Intent(this, SubColorActivity::class.java)
        //startActivity(intent)
        //startActivityForResult(intent, 99)
        lanzadorColorFavorito.launch(intent)//aquí lanzo la subactividad
    }

    fun mostrarInfoFormulario(view: View) {
        //mostrar los datos del formulario
        Log.d(Constantes.ETIQUETA_LOG, "NOMBRE = ${binding.editTextNombreFormulario.text.toString()} EDAD = ${binding.editTextEdadFormulario.text.toString()} HOMBRE = ${binding.radioButtonHombre.isChecked} MUJER = ${binding.radioButtonMujer.isChecked} MAYOR EDAD = ${binding.checkBox.isChecked}" )
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
        val uriFoto = if (binding.imagenFormulario.tag == null)
        {
            "" //no tiene foto
        } else
        {
            binding.imagenFormulario.tag as Uri //sí tiene foto
        }
        val usuario: Usuario = Usuario(nombre, edad, sexo, mayorEdad, this.color, uriFoto.toString())
        //val usuario: Usuario = Usuario(nombre, edad, sexo, mayorEdad)
        Log.d(Constantes.ETIQUETA_LOG, "USUARIO = $usuario" )
        guardarUsuario(usuario)
        //mostrar el SNACKBAR -> Mensaje Guardado
        val snackbar: Snackbar = Snackbar.make(binding.main, "USUARIO GUARDADO", BaseTransientBottomBar.LENGTH_LONG)
        snackbar.setAction ("DESHACER"){ v: View ->

            Log.d(Constantes.ETIQUETA_LOG, "HA TOCAO DESHACER")
            borrarUsuarioFichero()
        }
        //snackbar.setTextColor(getColor(R.color.mirojo))//color de la acción
        snackbar.show()
    }

    fun borrarUsuarioFichero ()
    {
        val fichero = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS, MODE_PRIVATE)
        fichero.edit(true) {
            clear()
        }

        //versión alternativa "tradicional"
        //val editor =  fichero.edit()
        //editor.clear()
        //editor.commit()
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
        val ficheroUsuario = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS, MODE_PRIVATE)

        val editor = ficheroUsuario.edit()//obtengo un editor para poder escribir a través de él en el fichero
        editor.putString("nombre", usuario.nombre)
        editor.putInt("edad", usuario.edad)
        editor.putString ("sexo", usuario.sexo.toString())
        editor.putInt ("color", usuario.colorFavorito)
        editor.putBoolean("mayorEdad", usuario.esMayorEdad)
        editor.putString("uriFoto", usuario.uriFoto)
        editor.apply()//o commit - guardo los cambios de verdad en el ficheros- se confirman, se hacen efectivos

    }

    fun esNombreValido (nombre:String): Boolean
    {
        var nombreValido: Boolean = false//declaro e inicializo

            nombreValido = if (nombre.length>2)
            {
                true
            } else {
                false
            }

            //nombre VAL
        //    nombreValido = (nombre.length > 2)

        return nombreValido

        //return nombre.length>2
    }

    fun borrarUsuarioPrefs(view: View) {
        borrarUsuarioFichero()
    }

    fun copiarImagenALocal(uri: Uri): Uri {
        val archivoGaleria = contentResolver.openInputStream(uri)
        val nombreArchivo = "imagen_formulario_perfil.jpg"
        val archivoNuevoSalida = File(filesDir, nombreArchivo)
        val outputStream = FileOutputStream(archivoNuevoSalida)

        archivoGaleria?.copyTo(outputStream)

        archivoGaleria?.close()
        outputStream.close()

        return Uri.fromFile(archivoNuevoSalida) // este sí puedes guardar y reutilizar
    }


}