package edu.adf.profe.contactos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.Constantes
import edu.adf.profe.R

/**
 * A diferencia de la actividad SeleccionContacto, aquí sí leemos todos los contactos (no sólo uno)
 * por lo que necesitamos obtener el permiso de READ_CONTACTS, que además al ser peligroso
 * necesitamos pedirlo en ejecución
 */
class SeleccionContactoPermisosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_contacto_permisos)
        //si tengo el permiso concecido
            //leemos los contactos
        //si no, lo pido
            //si es la primera vez, le explico
        /*
         val permisoConcedido = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permisoConcedido == PackageManager.PERMISSION_GRANTED)
         */

        //EN LA PRÁCTICA, PEDIMOS SIEMPRE
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 300 )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(Constantes.ETIQUETA_LOG, "A la vuelta de pedir persmiso de lectura")
        if (requestCode==300)
        {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Log.d(Constantes.ETIQUETA_LOG, "Permiso Concedido")
                leerContactos()
            } else
            {
                Log.d(Constantes.ETIQUETA_LOG, "Permiso Denegado")
                Toast.makeText(this, "SIN permisos para leer contactos", Toast.LENGTH_LONG).show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun leerContactos ()
    {

    }
}