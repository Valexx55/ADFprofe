package edu.adf.profe.basedatos.repository

import androidx.room.Transaction
import edu.adf.profe.basedatos.dao.CocheDao
import edu.adf.profe.basedatos.dao.EmpleoDao
import edu.adf.profe.basedatos.dao.PersonaDao
import edu.adf.profe.basedatos.entity.Coche
import edu.adf.profe.basedatos.entity.Empleo
import edu.adf.profe.basedatos.entity.Persona

class Repositorio (private val personaDao: PersonaDao, private val empleoDao: EmpleoDao, private val cocheDao: CocheDao) {

    val todasLasPersonas = personaDao.obtenerTodas()

    suspend fun insertar(persona:Persona):Long
    {
       return personaDao.insertar(persona)
    }

    suspend fun borrar(persona:Persona)
    {
        personaDao.borrar(persona)
    }

    suspend fun contarPersonas():Int
    {
       return personaDao.countPersonas()
    }

    @Transaction
    suspend fun insertarPersonaYEmpleo (persona: Persona, empleo: Empleo)
    {
        val idpersona = insertar(persona)
        empleo.personaId = idpersona
        empleoDao.insert(empleo)
    }

    suspend fun insertarCoche (coche: Coche)
    {
        cocheDao.insertCoche(coche)
    }

    suspend fun borrarCoche (coche: Coche)
    {
        cocheDao.deleteCoche(coche)
    }

    suspend fun leerCochesPersona(personaId:Int):List<Coche>
    {
        return cocheDao.getCochesDePersona(personaId.toLong())
    }
}