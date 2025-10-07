package edu.adf.profe.basedatos.repository

import edu.adf.profe.basedatos.dao.PersonaDao
import edu.adf.profe.basedatos.entity.Persona

class Repositorio (private val personaDao: PersonaDao) {

    val todasLasPersonas = personaDao.obtenerTodas()

    suspend fun insertar(persona:Persona)
    {
        personaDao.insertar(persona)
    }

    suspend fun borrar(persona:Persona)
    {
        personaDao.borrar(persona)
    }

    suspend fun contarPersonas():Int
    {
       return personaDao.countPersonas()
    }
}