package edu.adf.profe.authfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import edu.adf.profe.Constantes
import edu.adf.profe.R

class RegistroActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = FirebaseAuth.getInstance()
    }

    fun registrarNuevoUsuario(view: View) {
        Log.d(Constantes.ETIQUETA_LOG, "En registrarNuevoUsuario ")
        val correoNuevo = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
        val passWordNueva = findViewById<EditText>(R.id.editTextTextPassword).text.toString()

        Log.d(Constantes.ETIQUETA_LOG, "Creando cuenta con $correoNuevo y $passWordNueva ")
        firebaseAuth.createUserWithEmailAndPassword(correoNuevo, passWordNueva)
            .addOnCompleteListener()
            {
                tarea -> if (tarea.isSuccessful)
                        {
                           Toast.makeText(this, "NUEVO USUARIO REGISTRADO", Toast.LENGTH_LONG).show()
                           finish()
                           startActivity(Intent(this, AuthenticationActivity::class.java))
                        } else {
                            Toast.makeText(this, "ERROR AL REGISTRAR EL USUARIO", Toast.LENGTH_LONG).show()
                        }
            }
    }
}