package edu.adf.profe.contactos

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.Constantes
import edu.adf.profe.R

class SeleccionContactoActivity : AppCompatActivity() {


    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        //esta función se ejecuta a la vuelta del listado de contactos
        //it
        resultado ->
        Log.d(Constantes.ETIQUETA_LOG, "A la vuelta de Contactos ...")
        if (resultado.resultCode== RESULT_OK)
        {
            Log.d(Constantes.ETIQUETA_LOG, "La selección del contacto fue BIEN")
            Log.d(Constantes.ETIQUETA_LOG, "uri contactos = ${resultado.data}")
            mostrarDatosContacto (resultado.data!!)

        } else {
            Log.d(Constantes.ETIQUETA_LOG, "La selección del contacto fue MAL")
        }


    }

    private fun mostrarDatosContacto(intent: Intent) {
        val cursor = contentResolver.query(intent.data!!, null, null, null, null )
        cursor!!.moveToFirst() //me pongo en la primera fila
        //y accedemos a las columnas nombre y número
        val columnaNombre = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
        val columnaNumero = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val nombre = cursor.getString(columnaNombre)
        val numero = cursor.getString(columnaNumero)
        Log.d(Constantes.ETIQUETA_LOG, "NOMBRE = $nombre y NÚMERO = $numero")
        cursor.close()

       /* with(cursor) {
            val columnaNombreW = getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val columnaNumeroW = getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val nombreW = getString(columnaNombreW)
            val numeroW = getString(columnaNumeroW)
            Log.d(Constantes.ETIQUETA_LOG, "NOMBRE = $nombreW y NÚMERO = $numeroW")
            close()
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_contacto)
        selectContact()

    }

    private fun selectContact() {

        Log.d(Constantes.ETIQUETA_LOG, "Lanzando la app de contactos")
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)

        if (intent.resolveActivity(packageManager)!=null)
        {
            Log.d(Constantes.ETIQUETA_LOG, "SÍ HAY una APP de contactos")
            startForResult.launch(intent)
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "NO HAY APP de contactos")
        }


    }
}