package edu.adf.profe.basedatos.converter

import androidx.room.TypeConverter
import edu.adf.profe.basedatos.entity.TipoContrato
import java.util.Date

class Conversor {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromTipoContrato(value: String?): TipoContrato? = value?.let { TipoContrato.valueOf(it) }

    @TypeConverter
    fun tipoContratoToString(tipo: TipoContrato?): String? = tipo?.name

}