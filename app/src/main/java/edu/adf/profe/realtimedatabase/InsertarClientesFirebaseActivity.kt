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
    var listaClientes = ArrayList<Cliente>()

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

    /*+
    Este método consulta todos los registros de la base de datos remota y los almacena
    en una lista local.

    Esta lista, se usa para las acciones de borrado por nombre y actualización, de manera
    que si no se ejecuta antes esta función, las de borrar y actualizar no marcharán
     */
    fun mostrarClientesFB(view: View) {
        Log.d(Constantes.ETIQUETA_LOG, "Mostrar clientes FB")
        //this.databaseReference.child("clientes").child("clave").get()//con esto accedemos a un dato concreto por su clave
        this.databaseReference.child("clientes").get().addOnSuccessListener { datos ->

            var clave = datos.key
            Log.d(Constantes.ETIQUETA_LOG, "Clave $clave")
            var lista = datos.value as Map<String, Map<String, Any>>
            var entradas = lista.entries
            var ncliens = entradas.size

            Log.d(Constantes.ETIQUETA_LOG, "Hay $ncliens clientes")

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

    fun borrarUltimoPorClave (view: View){

        if (listaClientes.size>0)
        {
            val claveUltimo = listaClientes.get(listaClientes.size-1).clave

            Log.d(Constantes.ETIQUETA_LOG, "Clave ultimo = $claveUltimo")

            claveUltimo.let {

                Log.d(Constantes.ETIQUETA_LOG, "A eliminar cliente con id clave $claveUltimo")
                val refCli =  FirebaseDatabase.getInstance(URL_REAL_TIME_DATABASE).getReference("clientes/$claveUltimo")

                refCli.removeValue()
                    .addOnSuccessListener {
                        Log.d(Constantes.ETIQUETA_LOG, "Cliente eliminado")
                        listaClientes.removeAt(listaClientes.size-1)
                        Toast.makeText(this, "CLIENTE ELIMINADO", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { e ->
                        Log.e(Constantes.ETIQUETA_LOG, "Error: ${e.message}")
                    }

            }
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "Sin clientes que borrar")
            Toast.makeText(this, "Sin clientes que borrar/n Clique mostrar primero", Toast.LENGTH_LONG).show()
        }



    }

    /*
    ojo porque para borrar por nombre u otro campo, hay que crear en la base de datos
    un índice previmanete, para poder consultar por ese campo, en la sección de rules tipo así

    {
  "rules": {
    ".read": "now < 1762642800000",  // 2025-11-9
    ".write": "now < 1762642800000",  // 2025-11-9
    "clientes": {
      ".indexOn": ["nombre"]    // Agrega un índice para el campo 'nombre'
    }

  }
}
     */
    fun borrarPorNombre (nombre:String){

        val dbRef = FirebaseDatabase.getInstance(URL_REAL_TIME_DATABASE).getReference("clientes")
        // Configurar la consulta para obtener clientes con un nombre específico
        val query = dbRef.orderByChild("nombre").equalTo(nombre)

        query.get().addOnSuccessListener { snapshot ->
            snapshot.children.forEach { childSnapshot ->
                // Eliminar cada cliente que coincida con el nombre
                childSnapshot.ref.removeValue()
                    .addOnSuccessListener {
                        Log.d(Constantes.ETIQUETA_LOG, "Cliente ${childSnapshot.key} eliminado.")
                    }
                    .addOnFailureListener {
                        Log.d(Constantes.ETIQUETA_LOG, "Error al eliminar cliente")
                    }
            }
        }.addOnFailureListener {

            Log.e(Constantes.ETIQUETA_LOG, " Error al realizar la consulta. $it")
        }


    }

    fun borrarUltimoPorNombre(view: View) {

        if (listaClientes.size>0)
        {
            val nombre = listaClientes.get(listaClientes.size-1).nombre
            Log.d(Constantes.ETIQUETA_LOG, "Borrar por nombre = $nombre")
            borrarPorNombre(nombre)

        } else {
            Log.d(Constantes.ETIQUETA_LOG, "Sin clientes que borrar")
            Toast.makeText(this, "Sin clientes que borrar/n Clique mostrar primero", Toast.LENGTH_LONG).show()
        }
    }


    fun actualizarPorId (view: View)
    {
        if (listaClientes.size>0)
        {
            var cliente = listaClientes.get(listaClientes.size-1)
            val dbRef = FirebaseDatabase.getInstance(URL_REAL_TIME_DATABASE).getReference("clientes")
            // Configurar la consulta para obtener clientes con un nombre específico
            cliente.edad = cliente.edad+1
            dbRef.child(cliente.clave).setValue(cliente)
                .addOnSuccessListener {
                Log.d(Constantes.ETIQUETA_LOG, "Cliente actualizado")
                Toast.makeText(this, "Cliente actualizado", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{
                    Log.e(Constantes.ETIQUETA_LOG, "Error al actualizar cliente $it", it)
                    Toast.makeText(this, "Error al actualizar cliente", Toast.LENGTH_LONG).show()
                }

        } else {
            Log.d(Constantes.ETIQUETA_LOG, "Sin clientes para actualizar")
            Toast.makeText(this, "Sin clientes que actualizar/n Clique mostrar primero", Toast.LENGTH_LONG).show()
        }




    }

    //TODO actualizar por nombre
    /*

database.child("users").child(userId).setValue(user)
    .addOnSuccessListener {
        // Write was successful!
        // ...
    }
    .addOnFailureListener {
        // Write failed
        // ...
    }
     */
}