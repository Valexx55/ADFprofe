package edu.adf.profe.productos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityListaProductosBinding
import edu.adf.profe.util.RedUtil
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
PASOS PARA CREAR UN RECYCLERVIEW (MOSTRAR UNA COLECCIÓN/LISTA/TABLA)

-- fase estática/compilación
1) DEFINIR EL RECYCLERVIEW EN EL XML
2) CREAR EL LAYOUT/FILA ITEM - ASPECTO
3) CREAR EL VIEWHOLDER
4) CREAR EL ADAPTER
-- fase dinámica/ejecución
5) OBTENER DATOS (RETROFIT HTTP https://my-json-server.typicode.com/miseon920/json-api/products)
6) INSTANCIAR EL ADAPTER PASÁNDOLE LOS DATOS DEL PUNTO 5
7) ASOCIO EL ADAPTER AL RECYCLER
8) DEFINIMOS UN LAYOUT MANAGER PARA EL RECYCLER



 */

class ListaProductosActivity : AppCompatActivity() {

    lateinit var listaProductos: ListaProductos
    lateinit var binding: ActivityListaProductosBinding
    lateinit var adapter: ProductosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //preparo RetroFit

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productoService = retrofit.create(ProductoService::class.java)

        if (RedUtil.hayInternet(this))
        {
            //el bloque que va dentro de este métod o, se ejecuta en un segundo plano (proceso a parte)
            Log.d(Constantes.ETIQUETA_LOG, "Hay internet, vamos a pedir ")
            val haywifi = RedUtil.hayWifi(this)
            Log.d(Constantes.ETIQUETA_LOG, "Es tipo wifi = ${haywifi} ")
            lifecycleScope.launch {
                Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 1")
                listaProductos = productoService.obtenerProductos()
                Log.d(Constantes.ETIQUETA_LOG, "RESPUESTA RX ...")
                listaProductos.forEach { Log.d(Constantes.ETIQUETA_LOG, it.toString()) }
                mostrarListaProductos (listaProductos)
            }

        }else
        {
            val noti = Toast.makeText(this, "SIN CONEXIÓN A INTERNET", Toast.LENGTH_LONG)
            noti.show()
        }
        Log.d(Constantes.ETIQUETA_LOG, "LANZNADO PETICIÓN HTTP 2")

    }

    private fun mostrarListaProductos(listaProductos: ListaProductos) {
        this.adapter = ProductosAdapter(listaProductos)
        binding.recViewProductos.adapter = this.adapter
        binding.recViewProductos.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false )
    }
}