package edu.adf.profe.biometrico

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.R

class BioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        checkBiometricAviability ()
    }

    private fun checkBiometricAviability() {

        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // El dispositivo tiene biometría (huella, rostro, etc.) y está configurada
                Log.d("MIAPP", "Biometría disponible y lista para usar")
                showBiometricPrompt()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                // El dispositivo no tiene hardware biométrico
                Log.d("MIAPP", "No hay hardware biométrico disponible")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                // El hardware biométrico está actualmente no disponible
                Log.d("MIAPP", "Hardware biométrico no disponible temporalmente")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // El dispositivo tiene hardware biométrico, pero no hay huellas o biometría registrada
                Log.d("MIAPP", "No hay datos biométricos registrados")
            }
            else -> {
                Log.d("MIAPP", "Estado biométrico desconocido")
            }
        }

    }



// Dentro de tu Activity o Fragment

    fun showBiometricPrompt() {

            val executor = ContextCompat.getMainExecutor(this)

            val biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        runOnUiThread {
                            // Aquí pones lo que quieres hacer al autenticarse correctamente
                            Toast.makeText(this@BioActivity, "Autenticación correcta", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        runOnUiThread {
                            Toast.makeText(this@BioActivity, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        runOnUiThread {
                            Toast.makeText(this@BioActivity, "Error: $errString", Toast.LENGTH_SHORT).show()
                        }
                    }
                })

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Inicia sesión con huella")
                .setSubtitle("Usa tu huella para iniciar sesión")
                .setNegativeButtonText("Cancelar")
                .build()

            biometricPrompt.authenticate(promptInfo)


    }

}