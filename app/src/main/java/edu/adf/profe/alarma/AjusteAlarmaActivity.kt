package edu.adf.profe.alarma

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.materialswitch.MaterialSwitch
import edu.adf.profe.Constantes
import edu.adf.profe.R

class AjusteAlarmaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ajuste_alarma)
        initActivity()
    }

    private fun initActivity() {
        //TODO esto debe ir en una clase
        //inicializo el estado del boton en coherencia con la preference guardada
        val boton =  findViewById<MaterialSwitch>(R.id.switch1)
        val ficherop = getSharedPreferences("ajustes", Context.MODE_PRIVATE)
        if (ficherop.getBoolean("ALARMA", false))
        {

            boton.isChecked =true
        } else
        {
            boton.isChecked =false
        }
    }

    fun programarAlarma(view: View) {
        val materialSwitch = view as MaterialSwitch
        when (materialSwitch.isChecked)
        {
            true ->{
                //programar la alarma
                Log.d(Constantes.ETIQUETA_LOG, "Boton Switch encendido")
                // TODO guardar los datos en un Preferences
                GestorAlarma.programarAlarma(this)
            }
            false -> {
                //desprogramar la alarma
                Log.d(Constantes.ETIQUETA_LOG, "Boton Switch apagado")
                GestorAlarma.desprogramarAlarma(this)
            }
        }
    }
}