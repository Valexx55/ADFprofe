package edu.adf.profe.googleauth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.util.LogUtil

class GoogleAuthActivity : AppCompatActivity() {

    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_google_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ventanaAuth()
    }

    private fun ventanaAuth() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )

// Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()} AUTENTICACIÓN EXITOSA ")
            Toast.makeText(this, "AUTENTICACIÓN EXITOSA ", Toast.LENGTH_SHORT).show()
            // ...
            mostrarInfoUser (user!!)

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    private fun mostrarInfoUser(user: FirebaseUser) {
        Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()} $user ")
        Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()} ${user.uid} ${user.displayName} ${user.photoUrl} ${user.email} ${user.phoneNumber}")
    }

    fun salirLogout(view: View) {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
                Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()} SALIMOS OK")
            }
    }
    fun borrarUsuarioServidor(view: View) {
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                // ...
                Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()} BORRAMOS OK")
            }
    }


}