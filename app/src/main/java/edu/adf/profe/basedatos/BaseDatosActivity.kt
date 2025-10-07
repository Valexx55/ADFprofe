package edu.adf.profe.basedatos

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.basedatos.entity.Persona
import edu.adf.profe.basedatos.viewmodel.PersonaViewModel
import edu.adf.profe.databinding.ActivityBaseDatosBinding

class BaseDatosActivity : AppCompatActivity() {

    val personas:MutableList<Persona> = mutableListOf()//creamos la lista de personas vacía
   lateinit var binding: ActivityBaseDatosBinding

   val personaViewModel:PersonaViewModel by viewModels()//aquí guardamos los datos de la pantalla


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_datos)

        //ligamos las actualizaciones automáticas de la lista

        personaViewModel.personas.observe(this, {
            personas -> Log.d(Constantes.ETIQUETA_LOG, "Personas = $personas")
        })

    }

    fun insertarPersona(view: View) {
        personaViewModel.insertar(Persona(nombre="Andrés", edad = 25))
        personaViewModel.contarPersonas()
    }
}