package edu.adf.profe.biometrico

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
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
}