package edu.adf.profe.perros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import edu.adf.profe.R
import edu.adf.profe.databinding.PerroFragmentBinding

class PerroFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //val vistaPerroFragment = inflater.inflate(R.layout.perro_fragment, container, false)
        val vistaPerroFragment = PerroFragmentBinding.inflate(inflater, container, false)

        //TODO RELLENAR EL FRAGMENT CON EL CONTENIDO DEL PERRO CONCRETO
        var url_foto = arguments?.getString("URL_FOTO")
        var leyenda = arguments?.getString("LEYENDA")

        vistaPerroFragment.idleyenda.text = leyenda
        //me tengo que descargar la foto y ponérsela al imageView
        Glide.with(this)
            .load(url_foto?.toUri())
            .into(vistaPerroFragment.fotoPerro)


        return vistaPerroFragment.root
    }
}