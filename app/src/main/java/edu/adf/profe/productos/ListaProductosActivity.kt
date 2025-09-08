package edu.adf.profe.productos

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.util.RedUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureNanoTime

class ListaProductosActivity : AppCompatActivity() {

    lateinit var listaProductos: ListaProductos
    lateinit var job:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_productos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //preparo RetroFit


        //unused param
        //usando este handler en el contexto de la corutina, se derivan
        //las excepciones que el try catch no captura
        //igualmente, tenemos el comportamiento extraño de no hallar el detalle de la execepción si unsamos exception.cause como tercer parámetro (parace ser problema de hilos propagando excepciones)
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e("CorrutinaError", "Error en corrutina: ${exception.message}", exception.cause)

        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com")
            //.baseUrl("https://my-json-server.typicode.es")//para probar la excepción
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productoService = retrofit.create(ProductoService::class.java)

        if (RedUtil.hayInternet(this))
        {
            //el bloque que va dentro de este métod o, se ejecuta en un segundo plano (proceso a parte)
            Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 0")


               job = lifecycleScope.launch(Dispatchers.IO+handler+CoroutineName("RutinaProductos"), CoroutineStart.LAZY) {
                   var t1 = System.currentTimeMillis();
                   val res = try {
                    delay(5000)
                    Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 1 ${coroutineContext[CoroutineName]?.name}")
                    listaProductos = productoService.obtenerProductos()

                    //TODO HACER UN RECYCLER PARA MOSTRAR LA LISTA DE PRODUCTOS
                    listaProductos
                    } catch (ex:ClassNotFoundException)
                    {
                        Log.e(Constantes.ETIQUETA_LOG, "Error al obtener el listado", ex.cause) //IMPORTANTE USAR CAUSE PARA OBTENER EL DETALLE DEL FALLO
                        null
                        throw ex

                    }
                    if (res!=null)
                    {
                        Log.d(Constantes.ETIQUETA_LOG, "RESPUESTA RX ...")
                        listaProductos.forEach { Log.d(Constantes.ETIQUETA_LOG, it.toString()) }
                    } else {

                        val noti = Toast.makeText(this@ListaProductosActivity, "ERROR AL OBTENER EL LISTADO DE PRODUCTOS", Toast.LENGTH_LONG)
                        noti.show()

                    }
                   var t2 = System.currentTimeMillis();
                   var t = t2-t1;
                   //Log.d(Constantes.ETIQUETA_LOG, "TIEMPO CON MAIN = $t ms")
                   Log.d(Constantes.ETIQUETA_LOG, "TIEMPO CON IO = $t ms")
                }

                job.start()//Lanzo la corutina ahora, por haber usado el modo start lazy; hasta que no lo ejecute yo, no se lanza



        }else
        {
            val noti = Toast.makeText(this, "SIN CONEXIÓN A INTERNET", Toast.LENGTH_LONG)
            noti.show()
        }
        Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 2")
        /*
        SI HAY CONEXIÓN A INTERNET
            PIDO EL LISTADO DE PRODUCTOS
            DESPUÉS, MUESTRO EL LISTADO RX
          SI NO
            MUESTRO UN TOAST O MENSAJE DE ERROR
        * */


    }

    fun cancelarPeticion(view: View) {
        job.cancel()//aunque se cancele ya ejecutado, no falla
    }
}