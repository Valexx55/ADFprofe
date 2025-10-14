package edu.adf.profe.basedatos.converter

import androidx.room.TypeConverter
import edu.adf.profe.basedatos.entity.Empleo
import edu.adf.profe.basedatos.entity.Empleo.TipoContrato
import java.util.Date

/**
*Esta clase, la tenemos para convertir Date en Long y Enum en String
 *y viceversa, ya que SQLite no soporta tipos Date ni Enum
 *Si tenemos un Date en Java, lo guardamos como Long ( y al leerlo, lo traducimos de Long a Date)
 *Si tenemos un Enum en kotlin, lo guardamos como String ( y al leerlo, lo traducimos de String a Enum)
 *Esta clase, la referimos en APP DATABASE (la definición de la base de datos9
*
*@see edu.adf.profe.basedatos.db.AppDatabase

 */
class Conversor {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromTipoContrato(value: String?): Empleo.TipoContrato? = value?.let { TipoContrato.valueOf(it) }

    @TypeConverter
    fun tipoContratoToString(tipo: Empleo.TipoContrato?): String? = tipo?.name

}