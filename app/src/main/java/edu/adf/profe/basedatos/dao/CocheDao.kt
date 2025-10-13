package edu.adf.profe.basedatos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.adf.profe.basedatos.entity.Coche

@Dao
interface CocheDao {
    @Query("SELECT * FROM Coche WHERE personaId = :personaId")
    suspend fun getCochesDePersona(personaId: Long): List<Coche>

    @Insert
    suspend fun insertCoche(coche: Coche)

    @Delete
    suspend fun deleteCoche(coche: Coche)

    @Update
    suspend fun updateCoche(coche: Coche)
}
