package edu.adf.profe.basedatos.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.adf.profe.Constantes
import edu.adf.profe.basedatos.UltimaOperacionBD
import edu.adf.profe.basedatos.db.AppDatabase
import edu.adf.profe.basedatos.entity.Persona
import edu.adf.profe.basedatos.repository.Repositorio
import kotlinx.coroutines.launch

class PersonaViewModel(application: Application):AndroidViewModel(application) {

    private val repository: Repositorio
    val personas: LiveData<List<Persona>>
    lateinit var ultimaOperacionBD:UltimaOperacionBD
    var posicionAfectada:Int = -1

    init {
        val dao = AppDatabase.getDatabase(application).personaDao()
        repository = Repositorio(dao)
        personas = repository.todasLasPersonas
        ultimaOperacionBD = UltimaOperacionBD.NINGUNA
    }

    /*fun insertar(persona: Persona) = viewModelScope.launch {
        repository.insertar(persona)
    }*/

    fun insertar(persona: Persona, pos:Int) {
        viewModelScope.launch {
            repository.insertar(persona)
            ultimaOperacionBD = UltimaOperacionBD.INSERTAR
            posicionAfectada = pos
        }
    }

    fun borrar(persona: Persona, pos:Int) {
        viewModelScope.launch {
            repository.borrar(persona)
            ultimaOperacionBD = UltimaOperacionBD.BORRAR
            posicionAfectada = pos
        }
    }

    fun contarPersonas() {
        viewModelScope.launch {
            val nper = repository.contarPersonas()
            Log.d(Constantes.ETIQUETA_LOG, "numpersonas  $nper")
        }
    }
}