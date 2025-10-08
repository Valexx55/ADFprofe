package edu.adf.profe.basedatos.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.adf.profe.Constantes
import edu.adf.profe.basedatos.dao.PersonaDao
import edu.adf.profe.basedatos.entity.Persona
import java.util.concurrent.Executors

@Database(entities = [Persona::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "personas_db"
                ).setQueryCallback(
                    { consulta, parametros ->
                        Log.d(Constantes.ETIQUETA_LOG, "Consulta $consulta Parámetros $parametros")
                    }, Executors.newSingleThreadExecutor()
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}