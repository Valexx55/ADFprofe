package edu.adf.profe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.adf.profe.databinding.ActivityBusquedaBinding
import androidx.core.net.toUri

class BusquedaActivity : AppCompatActivity() {

    /**
     * VER
     * +
     * https://www.google.com/search?q=real+madrid
     *
     * -->
     *
     * ME abre el navegador web de mi dispositivo
     */

    lateinit var binding: ActivityBusquedaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun buscarEnGoogle(view: View) {
        //obtener el término de búsqueda introducido por el usuario
        val busqueda: String = binding.textoBusqueda.text.toString()
        //lanzar un intent para buscar en google
        Log.d("MIAPP", "El usuario quiere buscar $busqueda")

        //https://www.google.com/search?q=real+madrid
        val url: String = "https://www.google.com/search?q=$busqueda"
        val web: Uri = url.toUri() //  Uri.parse(url) //para eliminar espacios, tildes, la url la formamos bien con este método
        val intentBusqueda = Intent(Intent.ACTION_VIEW, web) //INTENT IMPLÍCITO
        if (intentBusqueda.resolveActivity(packageManager) != null) {
            Log.d("MIAPP", "El dispositivo puede navegar por internet")
            startActivity(intentBusqueda)
        } else {
            Toast.makeText(this, "No se ha detectado un navegador", Toast.LENGTH_LONG).show()
        }
         /*   val intentTienda = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.android.chrome"))
            Log.d("MIAPP", "Le invitamos a que instale Google Chrome de la tienda")
            if (intentTienda.resolveActivity(packageManager) != null) {
                startActivity(intentTienda)
            } else {
                Log.d("MIAPP", "El dispositivo ni tiene navegador ni tiene Play Store")

            }
    */

        }

}