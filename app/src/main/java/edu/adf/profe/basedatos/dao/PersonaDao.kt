package edu.adf.profe.basedatos.dao

import androidx.lifecycle.LiveData
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
    suspend fun insertar(persona: Persona):Long

    @Delete
    suspend fun borrar(persona: Persona)

    // TODO dato curioso de funcionamineto: cuando
    // se ejecuta un método del dao, se ejecutan
    // automáticamente los métodos que devuelven LIVEDATA
    // y su vez, se propagan los cambios a los suscriptores
    // de ese livedata

    //
    //@Query("SELECT * FROM personas WHERE id=1")
    @Query("SELECT * FROM personas ORDER BY nombre ASC")
    fun obtenerTodas():LiveData<List<Persona>>
    //TODO suspend probar

    @Query("SELECT COUNT(*) FROM personas")
    suspend fun countPersonas(): Int
}