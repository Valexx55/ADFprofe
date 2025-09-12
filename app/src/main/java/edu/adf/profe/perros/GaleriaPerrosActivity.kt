package edu.adf.profe.perros

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityGaleriaPerrosBinding
import edu.adf.profe.util.RedUtil
import kotlinx.coroutines.launch
import kotlin.math.abs

class GaleriaPerrosActivity : AppCompatActivity() {


    //TODO:
    // PONED LA RAZA DE PERRO CORRECTA EN EL LAYOUT
    // INCLUID EN EL FRAGMENT UNA LEYENDA CON LA SERIE DE LA FOTO TIPO 1 DE 20
    // Y QUE SE VAYA ACTUALIZANDO
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
        galeriaPerrosBinding.razaPerro.text = raza
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
/*
        galeriaPerrosBinding.viewPager2.setPageTransformer { page, position ->
            when {
                position < -1 -> page.alpha = 0f
                position <= 0 -> {
                    page.alpha = 1f
                    page.translationX = 0f
                    page.scaleX = 1f
                    page.scaleY = 1f
                }
                position <= 1 -> {
                    page.alpha = 1 - position
                    page.translationX = page.width * -position
                    val scaleFactor = 0.75f + (1 - position) * 0.25f
                    page.scaleX = scaleFactor
                    page.scaleY = scaleFactor
                }
                else -> page.alpha = 0f
            }*/

        /*
            val zoomOutTransformer = ViewPager2.PageTransformer { page, position ->
                val scale = 1 - abs(position) * 0.2f
                page.scaleX = scale
                page.scaleY = scale
                page.alpha = 0.5f + (1 - abs(position)) * 0.5f
            }*/

           // galeriaPerrosBinding.viewPager2.setPageTransformer(zoomOutTransformer)

            val fadeTransformer = ViewPager2.PageTransformer { page, position ->
                page.alpha = 1 - abs(position)
            }

          galeriaPerrosBinding.viewPager2.setPageTransformer(fadeTransformer)



        //galeriaPerrosBinding.viewPager2.setPageTransformer(depthTransformer)

    }
}