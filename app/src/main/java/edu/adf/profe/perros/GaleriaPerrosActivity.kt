package edu.adf.profe.perros

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityGaleriaPerrosBinding
import edu.adf.profe.util.RedUtil
import kotlinx.coroutines.launch

class GaleriaPerrosActivity : AppCompatActivity() {


    lateinit var galeriaPerrosBinding: ActivityGaleriaPerrosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galeriaPerrosBinding = ActivityGaleriaPerrosBinding.inflate(layoutInflater)
        setContentView(galeriaPerrosBinding.root)
        //TODO
        /*
         *1 obtener del Intent la info de la raza
         * 2 lanzar la petición GET con un corutina de retrofit al api de perros
         */
        val raza = intent.getStringExtra("RAZA") ?: ""
        if (RedUtil.hayInternet(this))
        {
            Log.d(Constantes.ETIQUETA_LOG, "Hay conexión a internet para atacar perros")
            lifecycleScope.launch {
                try{
                    val perroService =  PerrosRetrofitHelper.getPerrosServiceInstance()
                    val fotosRazaPerros = perroService.obtenerFotosRaza(raza)
                    mostrarFotosPerros(fotosRazaPerros)
                }catch (ex: Exception){
                    //sección paracaídas
                    Log.e(Constantes.ETIQUETA_LOG, "ERROR AL CONECTARNOS AL API DE FOTOS DE PERROS", ex)
                    Toast.makeText(this@GaleriaPerrosActivity, "FALLO AL OBTENER LAS FOTOS", Toast.LENGTH_LONG).show()
               }

            }
        }

    }

    fun mostrarFotosPerros(listaInternetFotosPerros: FotosRazaPerros): Unit{
        Log.d(Constantes.ETIQUETA_LOG, listaInternetFotosPerros.toString() )
        Log.d(Constantes.ETIQUETA_LOG, "HEMOS RX ${listaInternetFotosPerros.message.size} fotos" )
        //TODO mostrar las razas
        var adapterPerrosFragment = AdapterPerrosFragment(this)
        adapterPerrosFragment.fotosRazaPerros = listaInternetFotosPerros
        galeriaPerrosBinding.viewPager2.adapter = adapterPerrosFragment

    }
}