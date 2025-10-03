package edu.adf.profe.fechayhora

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivitySeleccionFechaYhoraBinding

class SeleccionFechaYHoraActivity : AppCompatActivity(), View.OnFocusChangeListener {

    lateinit var binding: ActivitySeleccionFechaYhoraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionFechaYhoraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cajaFecha.onFocusChangeListener = this //con esto, digo que cuando la caja tome el foco
        binding.cajaHora.onFocusChangeListener = this //el cursor esté ahí, se llama esa función
    }

    override fun onFocusChange(caja: View?, tieneFoco: Boolean) {
        //TODO("Not yet implemented")
        if (tieneFoco)
        {
            Log.d(Constantes.ETIQUETA_LOG, "FOCO GANADO")
            when (caja?.id)
            {
                R.id.cajaHora ->
                {
                    Log.d(Constantes.ETIQUETA_LOG, "La caja hora ha ganado el foco")
                    //creamos el fragmet
                    val dialogoFragmentReloj: DialogFragment = SeleccionHora()
                    //lo visualizamos
                    dialogoFragmentReloj.show(supportFragmentManager, "RELOJ")

                }
                R.id.cajaFecha ->
                {
                    Log.d(Constantes.ETIQUETA_LOG, "La caja fecha ha ganado el foco")
                    //creamos el fragmet
                    val dialogoFragmentCalendario: DialogFragment = SeleccionFecha()
                    //lo visualizamos
                    dialogoFragmentCalendario.show(supportFragmentManager, "CALENDARIO")
                }

            }
        }
    }


    fun actualizarHoraSeleccionada (hora:String)
    {
        this.binding.cajaHora.setText(hora)

    }

    fun actualizarFechaSeleccionada (fecha:String)
    {
        this.binding.cajaFecha.setText(fecha)

    }
}