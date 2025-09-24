package edu.adf.profe.foto

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityFotoBinding
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class FotoActivity : AppCompatActivity() {

    lateinit var binding:ActivityFotoBinding
    lateinit var uriFotoPrivada:Uri
    lateinit var uriFotoPublica:Uri
    //asociamos a esta actividad, su clase ViewModel (Modelo/estado)
    private val viewModel: FotoViewModel by viewModels()//obtenemos una instancia ya implementada: es delegación de propiedad, no de implementación
    //val viewModel: FotoViewModel = ViewModelProvider(this).get(FotoViewModel::class.java)


    val launcherIntentFoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
            resultado ->
        if (resultado.resultCode== RESULT_OK)
        {
            Log.d(Constantes.ETIQUETA_LOG, "La foto fue bien")
            binding.fotoTomada.setImageURI(this.uriFotoPublica)
            viewModel.uriFoto = this.uriFotoPublica
            actualizarGaleria()
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "La foto fue mal")
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.uriFoto?.let {
            binding.fotoTomada.setImageURI(it)
        }
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
        //1 CREAMOS UN FICHERO DESTINO
       val uri = crearFicheroDestino()

        uri?.let { //si uri es != null
            this.uriFotoPublica = it
            Log.d(Constantes.ETIQUETA_LOG, "URI FOTO = ${this.uriFotoPublica}")
            val intentFoto = Intent()
            intentFoto.setAction(MediaStore.ACTION_IMAGE_CAPTURE)
            intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, this.uriFotoPublica)
            launcherIntentFoto.launch(intentFoto)
        } ?: run {
            Toast.makeText(this, "NO FUE POSIBLE CREAR EL FICHERO DESTINO", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Scoped Storage (Almacenamiento con Ámbito) — Desde Android 11 (versión "R")
     * "A partir de Android R (Android 11), no podrás acceder al contenido de la carpeta interna compartida (/Android/data/, /Android/obb/) desde este gestor de archivos u otras apps.
     *
     * Con Scoped Storage, ninguna app puede acceder libremente al almacenamiento de otras apps o al directorio compartido /Android/data/, incluso aunque antes fuera posible
     *
     * Esto dice la teoría. Con navegadores de serie de algunos dispositivos sí se puede acceder
     *
     * Programáticamente se puede seguir accediendo y escribiendo sin permisos en el almacenamiento interno compartido
     * "
     */



    private fun crearFicheroDestino():Uri? {
        var rutaUriFoto:Uri? = null
        val fechaActual = Date()
        val momentoActual = SimpleDateFormat("yyyyMMdd_HHmmss").format(fechaActual)
        val nombreFichero = "FOTO_ADF_$momentoActual.jpg"
        //var rutaFoto =  "${Environment.getExternalStorageDirectory()?.path}/$nombreFichero" //ruta pública NO SE PUEDE - Security Exception
        //var rutaFoto =  "${Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_DOWNLOADS)?.path}/$nombreFichero" //ruta pública de DESCARGAS NO SE PUEDE - Security Exception /storage/emulated/0/Download/FOTO_ADF_20250923_10344 (EXPLORADOR) /storage/sdcard0/Download
        //var rutaFoto =  "${Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_DCIM)?.path}/$nombreFichero" //ruta pública de DESCARGAS NO SE PUEDE - Security Exception /storage/emulated/0/DCIM/FOTO_ADF_20250923_10344 (EXPLORADOR) /storage/sdcard0/DCIM
        //var rutaFoto =  "${getExternalFilesDir(null)?.path}/$nombreFichero" //ruta pública/privada /storage/emulated/0/Android/data/edu.adf.profe/files/FOTO_ADF_20250922_124524 (EXPLORADOR) /storage/sdcard0/Android/data/edu.adf.profe/files/FOTO_ADF_20250922_124524
        var rutaFoto = "${filesDir.path}/$nombreFichero" //ruta privada ruta completa fichero =  /data/user/0/edu.adf.profe/files/FOTO_ADF_20250922_122916 (EXPLORADOR) /data/data/edu.adf.profe/files/FOTO_ADF_20250922_122916
        //Log.d(Constantes.ETIQUETA_LOG, "ruta privada completa fichero =  $rutaFoto ")

        val ficheroFoto = File(rutaFoto)
        try{
            ficheroFoto.createNewFile()//este método tira una excepción, pero para KOTLIN todas las excepciones son de tipo RUNTIME o UnCHECKED - NO ME OBLIGA A GESTIONARLAS CON TRY/CATCH -
            uriFotoPrivada = ficheroFoto.toUri()
            Log.d(Constantes.ETIQUETA_LOG, "Fichero destino creado OK $uriFotoPrivada")
            uriFotoPublica = FileProvider.getUriForFile(this, "edu.adf.profe", ficheroFoto)
            Log.d(Constantes.ETIQUETA_LOG, "ruta pública $uriFotoPublica")
        } catch (e:Exception)
        {
            Log.e(Constantes.ETIQUETA_LOG, "Errro al crear el fichero destino de la foto", e)
        }

        //TODO CREAR RUTA PÚBLICA
        return uriFotoPublica
    }

    fun actualizarGaleria ()
    {
        MediaScannerConnection.scanFile(
            this,
            arrayOf(this.uriFotoPrivada.path),
            arrayOf("image/jpeg") // o el MIME correspondiente
        ) { path, uri ->
            Log.d("Scan", "Archivo escaneado: $path, URI: $uri")
        }

    }
}