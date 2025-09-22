package edu.adf.profe.foto

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityFotoBinding

class FotoActivity : AppCompatActivity() {

    lateinit var binding:ActivityFotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun tomarFoto(view: View) {
        pedirPermisos()
    }

    private fun pedirPermisos() {

        requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 500)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Log.d(Constantes.ETIQUETA_LOG, "PERMISO CÁMARA CONCEDIDO")
            lanzarCamara()
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "PERMISO CÁMARA NO CONCEDIDO")
            Toast.makeText(this, "SIN PERMISOS PARA HACER FOTOS", Toast.LENGTH_LONG).show()
        }
    }

    private fun lanzarCamara() {
        //TODO("Not yet implemented")
    }
}