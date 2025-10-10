package edu.adf.profe.realtimedatabase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityInsertarClientesFirebaseBinding

const val URL_REAL_TIME_DATABASE =
    "https://adfprofe-default-rtdb.europe-west1.firebasedatabase.app/"

class InsertarClientesFirebaseActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference //es el objeto para manejar la bd
    lateinit var binding: ActivityInsertarClientesFirebaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertarClientesFirebaseBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.databaseReference = FirebaseDatabase.getInstance(URL_REAL_TIME_DATABASE).reference
    }

    fun crearClienteFB(view: View) {

        val cliente = Cliente(
            binding.edadCliente.text.toString().toLong(),
            binding.localidadCliente.text.toString(),
            binding.nombreCliente.text.toString(),
            binding.emailCliente.text.toString()
        )

        //generamos la clave
        val idCliente = this.databaseReference.push().key
        cliente.clave = idCliente!!

        //insertamos en bd remota
        this.databaseReference.child("clientes").child(cliente.clave).setValue(cliente)
            .addOnCompleteListener { tarea ->
                Toast.makeText(this, "CLIENTE INSERTADO", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "ERROR INSERTANDO CLI", Toast.LENGTH_LONG).show()
                Log.e(Constantes.ETIQUETA_LOG, "Error al insertar ", it)
            }

    }

    fun mostrarClientesFB(view: View) {
        Log.d(Constantes.ETIQUETA_LOG, "Mostrar clientes FB")
        this.databaseReference.child("clientes").get().addOnSuccessListener { datos ->

            var clave = datos.key
            Log.d(Constantes.ETIQUETA_LOG, "Clave $clave")
            var lista = datos.value as Map<String, Map<String, Any>>
            var entradas = lista.entries
            var ncliens = entradas.size
            Log.d(Constantes.ETIQUETA_LOG, "Hay $ncliens clientes")
            var listaClientes = ArrayList<Cliente>()
            var cliente: Cliente
            //para cada cliente, obtenemos los datos
            entradas.forEach { (claveId, valor) ->
                Log.d(Constantes.ETIQUETA_LOG, "idCliente = $claveId")
                Log.d(Constantes.ETIQUETA_LOG, "nombre = ${valor.get("nombre")}")
                Log.d(Constantes.ETIQUETA_LOG, "email = ${valor.get("email")}")
                Log.d(Constantes.ETIQUETA_LOG, "localidad = ${valor.get("localidad")}")
                Log.d(Constantes.ETIQUETA_LOG, "edad = ${valor.get("edad")}")
                cliente = Cliente(
                    valor.get("edad") as Long,
                    valor.get("localidad").toString(),
                    valor.get("nombre").toString(),
                    valor.get("email").toString(),
                    claveId
                )

                listaClientes.add(cliente)
                if (listaClientes.size == ncliens) {
                    listaClientes.forEachIndexed { n, c -> Log.d(Constantes.ETIQUETA_LOG, "Cliente $n = $c.toString()")}
                }
            }
        }
    }
}