package edu.adf.profe.basedatos.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    foreignKeys = [ForeignKey(
        entity = Persona::class,
        parentColumns = ["id"],
        childColumns = ["personaId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("personaId")]
)
data class Empleo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var personaId: Long,
    val nombre: String,
    val fechaInicio: Date,
    val salario: Double,
    val tipoContrato: TipoContrato
)

enum class TipoContrato {
    TEMPORAL,
    INDEFINIDO
}