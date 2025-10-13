package edu.adf.profe.basedatos.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Persona::class,
        parentColumns = ["id"],
        childColumns = ["personaId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("personaId")]
)
data class Coche(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val personaId: Long,
    val marca: String,
    val modelo: String,
    val matricula: String
)
