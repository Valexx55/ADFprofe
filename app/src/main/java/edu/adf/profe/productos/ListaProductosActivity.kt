package edu.adf.profe.productos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.util.RedUtil
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaProductosActivity : AppCompatActivity() {

    lateinit var listaProductos: ListaProductos

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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.es")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productoService = retrofit.create(ProductoService::class.java)

        if (RedUtil.hayInternet(this))
        {
            //el bloque que va dentro de este métod o, se ejecuta en un segundo plano (proceso a parte)
            Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 0")


                lifecycleScope.launch {
                    val res = try {

                    Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 1")
                    listaProductos = productoService.obtenerProductos()

                    //TODO HACER UN RECYCLER PARA MOSTRAR LA LISTA DE PRODUCTOS
                    listaProductos
                    } catch (ex:Exception)
                    {
                        Log.e(Constantes.ETIQUETA_LOG, "Error al obtener el listado", ex.cause) //IMPORTANTE USAR CAUSE PARA OBTENER EL DETALLE DEL FALLO
                        null

                    }
                    if (res!=null)
                    {
                        Log.d(Constantes.ETIQUETA_LOG, "RESPUESTA RX ...")
                        listaProductos.forEach { Log.d(Constantes.ETIQUETA_LOG, it.toString()) }
                    } else {

                        val noti = Toast.makeText(this@ListaProductosActivity, "ERROR AL OBTENER EL LISTADO DE PRODUCTOS", Toast.LENGTH_LONG)
                        noti.show()

                    }
                }



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
}