package edu.adf.profe.tabs

import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityTabsBinding

class TabsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTabsBinding
    lateinit var adapterTabs: AdapterTabs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.adapterTabs = AdapterTabs(this)
        this.binding.vpt.adapter = this.adapterTabs
        //asocio al tablayout el viewpager
        TabLayoutMediator(this.binding.tablayout, this.binding.vpt){
            tl, n -> tl.text = "VISTA ${n+1}"

        }.attach()
        TabLayoutMediator(this.binding.tablayout, this.binding.vpt, fun (tl, n){
            tl.text = "VISTA ${n+1}"
        }).attach()

        onBackPressedDispatcher.addCallback{
                Log.d(Constantes.ETIQUETA_LOG, "Ha tocado el botón hacia atrás 1")
                haciaAtras()
        }
    }
    fun haciaAtras ()
    {
        if (this.binding.vpt.currentItem == 0)//si estoy en el tab 1
        {
            finish() //salgo
        } else { //si no
            this.binding.vpt.currentItem = this.binding.vpt.currentItem-1 //me voy al tab anterior
        }
    }



//FORMA ANTIGUA DE ESCUCHAR EL BOTÓN HACIA ATRÁS
    //A PARTIR DEL API 33, DEPRECADO
/*
    override fun onBackPressed() {
        Log.d(Constantes.ETIQUETA_LOG, "Ha dado al botón de ir hacia atrás")
        if (this.binding.vpt.currentItem == 0)//si estoy en el tab 1
        {
            super.onBackPressed() //salgo
        } else { //si no
            this.binding.vpt.currentItem = this.binding.vpt.currentItem-1 //me voy al tab anterior
        }


    }*/

}