package edu.adf.profe.perros

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.adf.profe.Constantes

class AdapterPerrosFragment(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    var fotosRazaPerros: FotosRazaPerros? = null

    override fun createFragment(position: Int): Fragment {
        var perroFragment = PerroFragment()
        //TODO Obtener la info del perro correspondiente por la posición
        //preparar la info que luego se cargue en el fragment -- USAMOS UN BUNDLE
        val url = this.fotosRazaPerros?.message?.get(position)
        Log.d(Constantes.ETIQUETA_LOG, "A pintar foto ${url}")

        var bundle = Bundle()
        bundle.putString("URL_FOTO", url)//meto la información que quiero pintar en ese fragment
        var txtleyenda = "${position+1} de ${this.fotosRazaPerros?.message?.size}"
        bundle.putString("LEYENDA", txtleyenda)//meto la información que quiero pintar en ese fragment

        perroFragment.arguments = bundle//lo guardo en arguments (Bundle)



        return perroFragment
    }

    override fun getItemCount(): Int {
       return this.fotosRazaPerros?.message?.size ?: 0
    }

}