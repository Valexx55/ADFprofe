package edu.adf.profe

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import edu.adf.profe.databinding.ActivityPrensaBinding


class PrensaActivity : AppCompatActivity() {

    val web1: String = "https://as.com/"
    val web2: String = "https://www.marca.com/"
    val web3: String = "https://www.mundodeportivo.com/"
    val web4: String = "https://www.sport.es/es/"
    val listaUrls = listOf(web1, web2, web3, web4)

    lateinit var binding: ActivityPrensaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrensaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView1.settings.javaScriptEnabled = true
        binding.webView2.settings.javaScriptEnabled = true
        binding.webView3.settings.javaScriptEnabled = true
        binding.webView4.settings.javaScriptEnabled = true

        binding.webView1.loadUrl(web1)
        binding.webView2.loadUrl(web2)
        binding.webView3.loadUrl(web3)
        binding.webView4.loadUrl(web4)

        //programar los botones
        //1 obtengo los botones
        val lbotones = findViewsByType(binding.root, Button::class.java)
        Log.d(Constantes.ETIQUETA_LOG, "LA LISTA TIENE ${lbotones.size} BOTONES")
        lbotones.forEachIndexed { index, boton ->
            boton!!.tag = listaUrls[index]
        }
        lbotones.forEach{
            it!!.setOnClickListener {
                val url = it.tag as String
                val intentWeb = Intent(ACTION_VIEW, url.toUri())
                startActivity(intentWeb)
            }
        }

    }

    private fun findViewsByType(
        vista_raiz: ViewGroup,
        tipo_buscado: Class<*>
    ): List<View?> {
        var lvistas: MutableList<View?>? = null
        var nhijos = 0
        var vactual: ViewGroup? = null
        var vistahija: View? = null

        val lista_vistas: MutableList<ViewGroup> = ArrayList()
        lista_vistas.add(vista_raiz)
        lvistas = ArrayList()

        var i = 0;
        do  {
            vactual = lista_vistas[i]
            Log.d("MIAPP", "Mostrando " + vactual.javaClass.canonicalName)
            nhijos = vactual.childCount
            for (j in 0..<nhijos) {
                vistahija = vactual.getChildAt(j)
                if (tipo_buscado.isAssignableFrom(vistahija.javaClass)) {
                    lvistas.add(vistahija)
                }
                if (vistahija is ViewGroup) {
                    lista_vistas.add(vistahija)
                } else {
                    Log.d("MIAPP", "Mostrando " + vistahija.javaClass.canonicalName)
                }
            }
            i++
        } while (i<lista_vistas.size)
        return lvistas
    }

}