package edu.adf.profe

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * SI TU IMC ESTÁ POR DEBAJO DE 16 , TU IMC ES DESNUTRIDO
 * SI TU IMC ES MAYOR O IGUAL A 16 Y MENOR QUE 18 --> DELGADO
 * SI TU IMC ES MAYOR O IGUAL A 18 Y MENOR QUE 25 --> IDEAL
 * SI TU IMC ES MAYOR O IGUAL A 25 Y MENOR QUE 31 --> SOBREPESO
 * SI TU IMC ES MAYOR O IGUAL que 31 --> OBESO
 */



class ImcActivity : AppCompatActivity() {

    var numeroVecesBoton: Int = 0 //PARA LLEVAR LA CUENTA de veces que el usuario toca el botón
    var resultadoNombre:String = "" //ámbito global/de clase

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Constantes.ETIQUETA_LOG, "en OnCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)
        //si bundle está a nulll
         //no hago nada
        //si es distinto de null
            //pillo el resultadoNombre
            //actualizo el valor de la caja
        if (savedInstanceState!=null)
        {
            Log.d(Constantes.ETIQUETA_LOG, "El saco tiene cosas. La actividad viene de recrearse")
            resultadoNombre = savedInstanceState.getString("resultadoNombre", "")
            val cajaResultado = findViewById<TextView>(R.id.imcResultado)
            cajaResultado.text = resultadoNombre
            cajaResultado.visibility = View.VISIBLE
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "La actividad se ha creado por primera vez")
        }
        //usando caracterísitcas "avanzadas" de Kotlin
        resultadoNombre = savedInstanceState?.getString("resultadoNombre") ?: "" //Operador ELVIS operator

    }

    //para dibujar un menú en la parte superior debo definir el  métod o onCreateOptionsMenu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_imc, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //este métod o es invocado cuando el usuario toca una opción del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.opcionSalir -> {
                Log.d(Constantes.ETIQUETA_LOG, "El usuario quiere salir")
                //finish()
                //preguntarle si quiere salir
                    //si nos confirma --> haremos finish
                    //si no quiere --> borrar el cuadro de dialogo
                //preparo el dialogo
                 var dialogo = AlertDialog.Builder(this)
                    .setTitle(R.string.titulo_dialogo_salir) //i18n
                    //.setTitle("AVISO")
                    .setMessage(R.string.mensaje_dialogo_salir)
                    //.setMessage("¿Desea Salir?")
                    .setIcon(R.drawable.imagen_derrota)
                    .setPositiveButton(R.string.boton_si){ dialogo, opcion ->
                        Log.d(Constantes.ETIQUETA_LOG, "Opción positiva salir =  $opcion")
                        this.finish()
                    }
                    .setNegativeButton(R.string.boton_no){ dialogo: DialogInterface, opcion: Int ->
                        Log.d(Constantes.ETIQUETA_LOG, "Opción negativa  =  $opcion")
                        dialogo.dismiss()
                    }
                    .setNeutralButton(R.string.boton_neutro){ dialogo: DialogInterface, opcion: Int ->
                        Log.d(Constantes.ETIQUETA_LOG, "Opción neutra  =  $opcion")
                        dialogo.cancel()
                    }
                    /*
                    dialogo.setOnCancelListener {
                        Log.d(Constantes.ETIQUETA_LOG, "HA CANCELADO EL DIALOGO")
                    }
                    dialogo.setOnCancelListener ( fun (dialogo: DialogInterface){
                        Log.d(Constantes.ETIQUETA_LOG, "HA CANCELADO EL DIALOGO")
                    })*/

                     /*
                    .setPositiveButton("SÍ", fun (dialogo: DialogInterface, opcion: Int){
                        this.finish()
                    })
                    .setNegativeButton("NO",fun (dialogo: DialogInterface, opcion: Int){
                        dialogo.cancel()
                        //dialogo.dismiss()
                    })*/

                dialogo.show()//lo muestro

            }
            R.id.opcionLimpiar -> {
                Log.d(Constantes.ETIQUETA_LOG, "El usuario quiere limpiar el formulario")
                //cojo las cajas de texto
                findViewById<EditText>(R.id.cajaPeso).setText("")
                // y las pongo a vacío
                findViewById<EditText>(R.id.cajaAltura).setText("")
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        super.onStart()
        Log.d(Constantes.ETIQUETA_LOG, "en onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(Constantes.ETIQUETA_LOG, "en onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(Constantes.ETIQUETA_LOG, "en onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(Constantes.ETIQUETA_LOG, "en onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Constantes.ETIQUETA_LOG, "en onDestroy")
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(Constantes.ETIQUETA_LOG, "en onSaveInstanceState")
        //guardo resultadoNombre en el saco "Bundle"
        outState.putString("resultadoNombre", resultadoNombre)
    }


    fun calcularImc(view: View) {

        Log.d(Constantes.ETIQUETA_LOG, "El usuario ha tocado el botón de calcular IMC")
        informarNumVecesBoton()
        //IMC = peso / (altura al cuadrado)  DIVISIÓN --> / multiplicación *
        //1 obtener el peso introducido
        val peso:Float = obtenerPeso() //variable local
        Log.d(Constantes.ETIQUETA_LOG, "PESO introducido $peso")
        //2 obtener la altura introducida
        val altura = obtenerAltura()
        Log.d(Constantes.ETIQUETA_LOG, "ALTURA introducido $altura")
        //3 hacer la fórmula del imc - PARÁMETROS ACTUALES
        val resultadoImc : Float = calculoImcNumerico(peso, altura) //LLAMADA/INVOKE - debe haber una coincidencia entre el tipo, número y el orden de los parámetros/variables
        Log.d(Constantes.ETIQUETA_LOG, "ALTURA introducido $resultadoImc")
        //4 mostrar el resultado
        mostrarResultado(resultadoImc)
        resultadoNombre = traducirResultadoImcVersionIF(resultadoImc)
        Log.d(Constantes.ETIQUETA_LOG, "RESULTADO IMC = $resultadoNombre")
        val tvresultado =  findViewById<TextView>(R.id.imcResultado)
        tvresultado.text = resultadoNombre
        tvresultado.visibility = View.VISIBLE
        // transitar a la ventana nueva ImagenResultadoActivity
        val intent:Intent = Intent(this, ImagenResultadoActivity::class.java)
        intent.putExtra("resultado", resultadoNombre)//"guardando en el saco"
        startActivity(intent)

    }

    fun mostrarResultado(resultadoImc: Float) //: Unit - void no devuelvo nada - no tengo return
    {
        val mensaje: Toast = Toast.makeText(this, "SU IMC ES $resultadoImc", Toast.LENGTH_LONG)
        mensaje.show()
    }

    //PARÁMETROS FORMALES
    fun calculoImcNumerico(peso:Float, altura:Float): Float //CABECERA
    {
        //1 declaro una variable del tipo devuelto
        var imc:Float = 0f

            imc = peso / (altura*altura)

        return imc
    }

    fun obtenerPeso (): Float
    {
        //uso var cuando la variable decalarada puede cambiar su valor después (mutable)
        var peso:Float = 0f //inicializo la variable del peso

            //tengo que coger el valor de la caja
            //obtener la caja
            //cuando tenga una variable/dato que no cambia su valor, uso val
            val editTextPeso: EditText = findViewById<EditText>(R.id.cajaPeso)
            //"33" text --> 33.0 -- float
            peso = editTextPeso.text.toString().toFloat() //accedo al contenido y lo traduzco a número


        return peso
    }


    fun obtenerAltura (): Float
    {
        //uso var cuando la variable decalarada puede cambiar su valor después (mutable)
        var altura:Float = 0f //inicializo la variable del peso

        //tengo que coger el valor de la caja
        //obtener la caja
        //cuando tenga una variable/dato que no cambia su valor, uso val
        val editTextAltura: EditText = findViewById<EditText>(R.id.cajaAltura)
        //"33" text --> 33.0 -- float
        altura = editTextAltura.text.toString().toFloat() //accedo al contenido y lo traduzco a número


        return altura
    }

    fun informarNumVecesBoton ()
    {
        numeroVecesBoton = numeroVecesBoton+1; // + string concatenar --> unir palabras // + números sumar
        val toast:Toast = Toast.makeText(this, "El usuario ha tocado el botón de calcular IMC $numeroVecesBoton veces ", Toast.LENGTH_LONG)
        //mostrar la notificación
        toast.show()
    }

    fun traducirResultadoImcVersionIF(resultado:Float):String
    {
        var imcResultado:String = ""

        if (resultado<16) {
            imcResultado = "DESNUTRIDO"
        } else if (resultado>=16 && resultado<18)
        {
            imcResultado = "DELGADO"
        } else if (resultado>=18 && resultado <25)
        {
            imcResultado = "IDEAL"
        } else if (resultado>=25 && resultado<31)
        {
            imcResultado = "SOBREPESO"
        } else if (resultado>=31)
        {
            imcResultado = "OBESO"
        }

        return imcResultado
    }


    /**
     *
     * public static Toast makeText(
     *     android.content.Context context,
     *     CharSequence text,
     *     int duration
     * )
     */
}