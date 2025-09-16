package edu.adf.profe.canciones

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityBusquedaCancionesBinding

class BusquedaCancionesActivity : AppCompatActivity() {

    lateinit var binding: ActivityBusquedaCancionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityBusquedaCancionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // this.binding.cajaBusqueda.requestFocus()
        this.binding.cajaBusqueda.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               Log.d(Constantes.ETIQUETA_LOG, "Buscando $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() ?: false)
                {
                    Log.d(Constantes.ETIQUETA_LOG, "CAJA LIMPIA")
                }
                else {
                    Log.d(Constantes.ETIQUETA_LOG, "Cambio $newText")
                }

                return true
            }

        })
    }

   /** override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_canciones, menu)
        val menuItemBusqueda = menu?.findItem(R.id.menuBusqueda)
        val searchView = menuItemBusqueda?.actionView as SearchView
        searchView.queryHint = "Intro nombre o canción"
        return true
    }*/
}


