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

const val URL_REAL_TIME_DATABASE = "https://adfprofe-default-rtdb.europe-west1.firebasedatabase.app/"
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

        val cliente = Cliente(binding.edadCliente.text.toString().toInt(),
            binding.localidadCliente.text.toString(),
            binding.nombreCliente.text.toString(),
            binding.emailCliente.text.toString())

        //generamos la clave
        val idCliente = this.databaseReference.push().key
        cliente.clave = idCliente!!

        //insertamos en bd remota
        this.databaseReference.child("clientes").child(cliente.clave).setValue(cliente)
            .addOnCompleteListener{
                tarea ->
                Toast.makeText(this, "CLIENTE INSERTADO", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "ERROR INSERTANDO CLI", Toast.LENGTH_LONG).show()
                Log.e(Constantes.ETIQUETA_LOG, "Error al insertar ", it)
            }

    }
}