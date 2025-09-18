package edu.adf.profe.contactos

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
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
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 300)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(Constantes.ETIQUETA_LOG, "A la vuelta de pedir persmiso de lectura")
        if (requestCode == 300) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(Constantes.ETIQUETA_LOG, "Permiso Concedido")
                leerContactos()
            } else {
                Log.d(Constantes.ETIQUETA_LOG, "Permiso Denegado")
                Toast.makeText(this, "SIN permisos para leer contactos", Toast.LENGTH_LONG).show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun consultarTodosLosTelefonos(): Unit {
        Log.d(Constantes.ETIQUETA_LOG, "consultarTodosLosTelefonos")
        val telefonos: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        var numcolumna = 0
        var number: String? = ""

        while (telefonos?.moveToNext() == true) {
            numcolumna = telefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            number = telefonos.getString(numcolumna)
            Log.d(Constantes.ETIQUETA_LOG, "Telefono $number")
        }
        telefonos?.close()//Cierro el cursor!

    }


    fun leerContactos() {
        consultarTodosLosTelefonos()
        mostrarContactos("")
    }

    fun mostrarContactos (prefijo : String)
    {
        val uri_contactos = ContactsContract.Contacts.CONTENT_URI//content://com.android.contacts/contacts
        /*val cursor_contactos = contentResolver.query(
            uri_contactos,
            null,
            ContactsContract.Contacts.DISPLAY_NAME +" LIKE ?",
            arrayOf(prefijo),
            null
        )*/

        val cursor_contactos = contentResolver.query(
            uri_contactos,
            null,
            null,
            null,
            null
        )
        cursor_contactos.use {
            if (it?.moveToFirst() == true)
            {
                do {
                    Log.d(Constantes.ETIQUETA_LOG, "NUM CONTACTOS = " + it.count)

                    val numColId = it.getColumnIndex(ContactsContract.Contacts._ID)
                    val numColNombre = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

                    val id = it.getLong(numColId)
                    val nombre = it.getString(numColNombre)

                    Log.d(Constantes.ETIQUETA_LOG, "Nombre = $nombre ID = $id")
                    mostrarCuentaRaw(id)
                } while (it.moveToNext())
            }
        }

    }


    fun mostrarCuentaRaw (id:Long):Unit
    {
        var cursor_raw = contentResolver.query(
            ContactsContract.RawContacts.CONTENT_URI,
            null,
            ContactsContract.RawContacts.CONTACT_ID + " = " +id,
            null,
            null
        )
        if (cursor_raw?.moveToFirst()==true){
            do {
                val columnaIdRaw = cursor_raw.getColumnIndex(ContactsContract.RawContacts._ID)
                val id_raw = cursor_raw.getLong(columnaIdRaw)

                val tipoIdRaw = cursor_raw.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE)
                val tipo_raw = cursor_raw.getString (tipoIdRaw)

                val nombreCuentaIdRaw = cursor_raw.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME)
                val nombreCuenta_raw = cursor_raw.getString(nombreCuentaIdRaw)

                Log.d(Constantes.ETIQUETA_LOG, "(RAW) NOMBRE CUENTA = $nombreCuenta_raw TIPO CUENTA = $tipo_raw ID = $id_raw")

                mostrarDetalle(id_raw)


            } while (cursor_raw.moveToNext())
        }
        cursor_raw?.close()
    }

    fun mostrarDetalle (id_raw :Long):Unit
    {
        val cursor_data = contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            ContactsContract.Data.RAW_CONTACT_ID +" = " + id_raw,
            null,
            null
        )

        cursor_data?.use {
            //it es el cursor

            if (it.moveToFirst()==true)
            {
                do {
                    val tipoMimeCol = it.getColumnIndex(ContactsContract.Data.MIMETYPE)
                    val tipoMime = it.getString(tipoMimeCol)
                    val dataCol = it.getColumnIndex(ContactsContract.Data.DATA1)
                    val data = it.getString(dataCol)

                    Log.d(Constantes.ETIQUETA_LOG, "   (DATA) MIME = $tipoMime DATA = $data")

                } while (it.moveToNext())
            }
        } //se cierra el cursor automáticamente si lo uso con use

    }
}