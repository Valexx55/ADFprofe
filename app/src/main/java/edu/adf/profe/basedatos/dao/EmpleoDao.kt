package edu.adf.profe.basedatos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import edu.adf.profe.basedatos.entity.Empleo

@Dao
interface EmpleoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(empleo: Empleo)

    @Update
    suspend fun update(empleo: Empleo)

    @Delete
    suspend fun delete(empleo: Empleo)
}
