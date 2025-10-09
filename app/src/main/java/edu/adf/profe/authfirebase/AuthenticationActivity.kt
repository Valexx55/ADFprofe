package edu.adf.profe.authfirebase

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

class AuthenticationActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authentication)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = FirebaseAuth.getInstance()
    }

    fun login(view: View) {
        Log.d(Constantes.ETIQUETA_LOG, "En login ")
        val correoNuevo = findViewById<EditText>(R.id.editTextTextEmailAddressAuth).text.toString()
        val passWordNueva = findViewById<EditText>(R.id.editTextTextPasswordAuth).text.toString()

        Log.d(Constantes.ETIQUETA_LOG, "Logueando cuenta con $correoNuevo y $passWordNueva ")
        firebaseAuth.signInWithEmailAndPassword(correoNuevo, passWordNueva)
            .addOnCompleteListener()
            {
                    tarea -> if (tarea.isSuccessful)
            {
                Toast.makeText(this, "LOGIN CORRECTO", Toast.LENGTH_LONG).show()
                finish()
                //startActivity(Intent(this, AuthenticationActivity::class.java))
            } else {
                Toast.makeText(this, "ERROR AL hacer LOGIN", Toast.LENGTH_LONG).show()
            }
            }
    }
}