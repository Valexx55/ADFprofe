package edu.adf.profe.basedatos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import edu.adf.profe.basedatos.entity.Persona

/**
 * En esta clase describimos las operaciones que
 * se pueden realizar en la base de datos
 * con una persona
 */

@Dao
interface PersonaDao {

    @Insert
    suspend fun insertar(persona: Persona)

    @Delete
    suspend fun borrar(persona: Persona)

    @Query("SELECT * FROM personas ORDER BY nombre ASC")
    fun obtenerTodas():List<Persona>
    //TODO suspend probar
}