package edu.adf.profe.basedatos.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.adf.profe.Constantes
import edu.adf.profe.basedatos.converter.Conversor
import edu.adf.profe.basedatos.dao.CocheDao
import edu.adf.profe.basedatos.dao.EmpleoDao
import edu.adf.profe.basedatos.dao.PersonaDao
import edu.adf.profe.basedatos.entity.Coche
import edu.adf.profe.basedatos.entity.Empleo
import edu.adf.profe.basedatos.entity.Persona
import java.util.concurrent.Executors

@Database(entities = [Persona::class, Empleo::class, Coche::class], version = 1)
@TypeConverters(Conversor::class)//para que gaurde las fechas como timestamp y los enumerados como String
abstract class AppDatabase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao
    abstract fun empleoDao(): EmpleoDao
    abstract fun cocheDao(): CocheDao

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